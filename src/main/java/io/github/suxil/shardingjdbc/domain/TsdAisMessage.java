package io.github.suxil.shardingjdbc.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "tsd_ais_message", catalog = "ais 消息")
public class TsdAisMessage {

    private static final long serialVersionUID = 1L;

    public static final String MMSI = "mmsi";
    public static final String UTC = "utc";
    public static final String LAT = "lat";
    public static final String LON = "lon";
    public static final String AIS_TYPE = "ais_type";

    @Column(name = "mmsi")
    private int mmsi;

    @Column(name = "utc")
    private long utc;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lon")
    private double lon;

    @Column(name = "ais_type")
    private String aisType;

}
