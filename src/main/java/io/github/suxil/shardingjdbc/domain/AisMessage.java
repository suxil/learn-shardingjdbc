package io.github.suxil.shardingjdbc.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "ais_message", catalog = "ais 消息")
public class AisMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String MMSI = "mmsi";
    public static final String UTC = "utc";
    public static final String LAT = "lat";
    public static final String LON = "lon";
    public static final String AIS_TYPE = "ais_type";

    @Id
    @Column(name = "id", length = 32, unique = true, nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    String id;

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
