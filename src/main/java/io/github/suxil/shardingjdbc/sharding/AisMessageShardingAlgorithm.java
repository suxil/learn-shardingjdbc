package io.github.suxil.shardingjdbc.sharding;

import io.github.suxil.shardingjdbc.util.ShardingAlgorithmUtils;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class AisMessageShardingAlgorithm implements ComplexKeysShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection availableTargetNames, ComplexKeysShardingValue shardingValue) {
        String logicTableName = shardingValue.getLogicTableName();

        Map<String, Collection<Object>> shardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
        Map<String, Collection<Object>> rangeValuesMap = shardingValue.getColumnNameAndRangeValuesMap();

        Collection<Object> mmsiObjList = shardingValuesMap.get("mmsi");
        Collection<Object> utcObjList = shardingValuesMap.get("utc");
        Object range = rangeValuesMap.get("utc");

        Collection<Integer> mmsiList = new ArrayList<>();
        Collection<Long> utcList = new ArrayList<>();
        if (mmsiObjList != null) {
            mmsiObjList.forEach(item -> mmsiList.add(Integer.parseInt(String.valueOf(item))));
        }
        if (utcObjList != null) {
            utcObjList.forEach(item -> utcList.add(Long.parseLong(String.valueOf(item))));
        }

        Collection<String> result = ShardingAlgorithmUtils.tableList(logicTableName, mmsiList, utcList, range);

        return result;
    }
}
