package com.ujn.spu.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@DynamicInsert
@DynamicUpdate //更新变化的字段
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class PhoneSetEntity implements Serializable {
    private static final long serialVersionUID = -1489346913036389457L;
    @Id
    //@GeneratedValue
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String phone;
}
