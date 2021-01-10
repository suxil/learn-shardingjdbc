package io.github.suxil.shardingjdbc.sharding;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.text.SimpleDateFormat;
import java.util.*;

public class TsdAisMessageShardingAlgorithm implements ComplexKeysShardingAlgorithm {

    private static final Integer MMSI_REMINDER = 10;

    @Override
    public Collection<String> doSharding(Collection availableTargetNames, ComplexKeysShardingValue shardingValue) {
        Collection<String> result = new ArrayList<>();

        String logicTableName = shardingValue.getLogicTableName();

        Map<String, Collection<Object>> shardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
        Map<String, Collection<Object>> rangeValuesMap = shardingValue.getColumnNameAndRangeValuesMap();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMM");

        Collection<Object> mmsiList = null;

        if (shardingValuesMap.containsKey("mmsi")) {
            mmsiList = shardingValuesMap.get("mmsi");
        } else {
            mmsiList = new ArrayList<>();
            for (int i = 0; i < MMSI_REMINDER; i++) {
                mmsiList.add(i);
            }
        }

        for (Object mmsi : mmsiList) {
            int idx = Integer.parseInt(String.valueOf(mmsi)) % MMSI_REMINDER;

            if (shardingValuesMap.containsKey("utc")) {
                for (Object utc : shardingValuesMap.get("utc")) {
                    Date date = new Date(Long.parseLong(String.valueOf(utc)));

                    String tableName = String.format("%s_%s_%s", logicTableName, simpleDateFormat.format(date), idx);

                    result.add(tableName);
                }
            }

            if (rangeValuesMap.containsKey("utc")) {
                Object range = rangeValuesMap.get("utc");
                if (range instanceof Range) {
                    if (((Range<?>) range).hasLowerBound()) {
                        Object lowerVal = ((Range<?>) range).lowerEndpoint();
                        if (lowerVal instanceof Long) {
                            Date date = new Date((Long) lowerVal);

                            String tableName = String.format("%s_%s_%s", logicTableName, simpleDateFormat.format(date), idx);

                            result.add(tableName);
                        }
                    }
                    if (((Range<?>) range).hasUpperBound()) {
                        Object upperVal = ((Range<?>) range).upperEndpoint();
                        if (upperVal instanceof Long) {
                            Date date = new Date((Long) upperVal);

                            String tableName = String.format("%s_%s_%s", logicTableName, simpleDateFormat.format(date), idx);

                            result.add(tableName);
                        }
                    }
                }
            }
        }

        return result;
    }
}
