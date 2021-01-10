package io.github.suxil.shardingjdbc.mapper;

import io.github.suxil.shardingjdbc.domain.AisMessage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AisMessageRowMapper implements RowMapper<AisMessage> {

    @Override
    public AisMessage mapRow(ResultSet resultSet, int i) throws SQLException {
        AisMessage aisMessage = new AisMessage();
        aisMessage.setId(resultSet.getString("id"));
        aisMessage.setMmsi(resultSet.getInt("mmsi"));
        aisMessage.setUtc(resultSet.getLong("utc"));
        aisMessage.setLat(resultSet.getDouble("lat"));
        aisMessage.setLon(resultSet.getDouble("lon"));
        aisMessage.setAisType(resultSet.getString("ais_type"));
        return aisMessage;
    }

}
