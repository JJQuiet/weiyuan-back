package com.ujn.spu.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historical_location_trajectories")
public class HistoryLocationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userName;

    @Column(name = "latitude")
//    private Double latitude;
    private String latitude;

    @Column(name = "longitude")
//    private Double longitude;
    private String longitude;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public void setUserName(String cameraName) {
        this.userName = cameraName;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


    // 省略构造函数、getter和setter方法
}
