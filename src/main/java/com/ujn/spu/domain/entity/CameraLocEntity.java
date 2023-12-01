package com.ujn.spu.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@DynamicInsert
@DynamicUpdate //更新变化的字段
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class CameraLocEntity implements Serializable {
    private static final long serialVersionUID = -1489346913092385487L;
    @Id
    //@GeneratedValue
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String cameraName;
    private String cameraAddr;
    private String lngVal;
    private String latVal;

}
