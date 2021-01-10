package io.github.suxil.shardingjdbc.util;

import com.google.common.collect.Range;
import org.apache.commons.collections4.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ShardingAlgorithmUtils {

    public static final Integer MMSI_REMINDER = 10;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMM");

    public static Collection<String> tableList(String logicTableName, Collection<Integer> mmsiList, Collection<Long> utcList, Object range) {
        Long lowerUtc = null;
        Long upperUtc = null;

        if (range != null) {
            if (range instanceof Range) {
                if (((Range<?>) range).hasLowerBound()) {
                    Object lowerVal = ((Range<?>) range).lowerEndpoint();
                    if (lowerVal instanceof Long) {
                        lowerUtc = (Long) lowerVal;
                    }
                }
                if (((Range<?>) range).hasUpperBound()) {
                    Object upperVal = ((Range<?>) range).upperEndpoint();
                    if (upperVal instanceof Long) {
                        upperUtc = (Long) upperVal;
                    }
                }
            }
        }
        return tableList(logicTableName, mmsiList, utcList, lowerUtc, upperUtc);
    }

    public static Collection<String> tableList(String logicTableName, Collection<Long> utcList) {
        return tableList(logicTableName, null, utcList, null, null);
    }

    public static Collection<String> tableList(String logicTableName, Collection<Integer> mmsiList, Collection<Long> utcList) {
        return tableList(logicTableName, mmsiList, utcList, null, null);
    }

    public static Collection<String> tableList(String logicTableName, Collection<Integer> mmsiList, Long lowerUtc, Long upperUtc) {
        return tableList(logicTableName, mmsiList, null, lowerUtc, upperUtc);
    }

    /**
     * 返回条件组成的表名
     * @param logicTableName
     * @param mmsiList
     * @param utcList
     * @param lowerUtc
     * @param upperUtc
     * @return
     */
    public static Collection<String> tableList(String logicTableName, Collection<Integer> mmsiList, Collection<Long> utcList, Long lowerUtc, Long upperUtc) {
        if (CollectionUtils.isEmpty(mmsiList)) {
            mmsiList = new ArrayList<>();
            for (int i = 0; i < MMSI_REMINDER; i++) {
                mmsiList.add(i);
            }
        }

        List<String> result = new ArrayList<>();
        for (Integer mmsi : mmsiList) {
            int idx = mmsi % MMSI_REMINDER;

            if (CollectionUtils.isNotEmpty(utcList)) {
                for (Long utc : utcList) {
                    Date date = new Date(utc);

                    String tableName = String.format("%s_%s_%s", logicTableName, simpleDateFormat.format(date), idx);

                    result.add(tableName);
                }
            }

            if (lowerUtc != null) {
                Date date = new Date(lowerUtc);

                String tableName = String.format("%s_%s_%s", logicTableName, simpleDateFormat.format(date), idx);

                result.add(tableName);
            }

            if (upperUtc != null) {
                Date date = new Date(upperUtc);

                String tableName = String.format("%s_%s_%s", logicTableName, simpleDateFormat.format(date), idx);

                result.add(tableName);
            }
        }
        return result;
    }

}
