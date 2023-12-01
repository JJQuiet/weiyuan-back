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
public class PlanEntity implements Serializable {
    private static final long serialVersionUID = -1456486914092388487L;

    @Id
    //@GeneratedValue
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * addr id
     */
    private String addrId;
    /**
     * source_value
     */
    private String sourceValue;

    private String nameValue;

    private String contentValue;
}
