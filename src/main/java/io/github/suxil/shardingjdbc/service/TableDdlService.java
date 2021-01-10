package io.github.suxil.shardingjdbc.service;

import io.github.suxil.shardingjdbc.util.ShardingAlgorithmUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TableDdlService {

    public static final String AIS_MESSAGE_SEARCH_TABLE_NAME = "SELECT tablename FROM pg_tables where tablename like 'ais_message_%';";

    public static final String AIS_MESSAGE_TABLE_DDL = "create table %s (\n" +
            "  id varchar(32) not null,\n" +
            "  mmsi integer,\n" +
            "  utc bigint,\n" +
            "  lat decimal,\n" +
            "  lon decimal,\n" +
            "  ais_type varchar(20)\n" +
            ");";
    public static final String AIS_MESSAGE_DROP_TABLE_DDL = "drop table %s;";
    public static final String AIS_MESSAGE_TABLE_MMSI_UTC_INDEX_DDL = "CREATE INDEX %s_mmsi_utc_index ON %s (mmsi, utc);";
    public static final String AIS_MESSAGE_TABLE_UTC_INDEX_DDL = "CREATE INDEX %s_utc_index ON %s (utc);";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<String> getExistTable() {
        List<String> tableNameList = jdbcTemplate.queryForList(AIS_MESSAGE_SEARCH_TABLE_NAME, String.class);

        return tableNameList;
    }

    private List<String> getNotExistTable() {
        List<String> tableNameList = getExistTable();

        String logicTableName = "ais_message";

        Map<String, String> tableNameMap = new HashMap<>();
        for (String tableName : tableNameList) {
            tableNameMap.put(tableName, tableName);
        }

        List<Long> utcList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) - 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 12; j++) {
                int month = j;
                calendar.set(year, month, 1);

                utcList.add(calendar.getTime().getTime());
            }
            year = year + 1;
        }

        Collection<String> tableAllList = ShardingAlgorithmUtils.tableList(logicTableName, utcList);

        List<String> tableNotExistList = new ArrayList<>();
        for (String tableName : tableAllList) {
            if (!tableNameMap.containsKey(tableName)) {
                tableNotExistList.add(tableName);
            }
        }

        return tableNotExistList;
    }

    public void createAisMessageTableDdl() {
        List<String> tableNotExistList = getNotExistTable();

        for (String tableName : tableNotExistList) {
            jdbcTemplate.execute(String.format(AIS_MESSAGE_TABLE_DDL, tableName));
            jdbcTemplate.execute(String.format(AIS_MESSAGE_TABLE_MMSI_UTC_INDEX_DDL, tableName, tableName));
            jdbcTemplate.execute(String.format(AIS_MESSAGE_TABLE_UTC_INDEX_DDL, tableName, tableName));
        }
    }

    public void dropAisMessageTableDdl() {
        List<String> tableExistList = getExistTable();

        for (String tableName : tableExistList) {
            jdbcTemplate.execute(String.format(AIS_MESSAGE_DROP_TABLE_DDL, tableName));
        }
    }

}
