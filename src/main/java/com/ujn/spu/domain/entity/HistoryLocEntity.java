package com.ujn.spu.domain.entity;

import lombok.Data;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;



/**
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "historyloc_tbl")
public class HistoryLocEntity implements Serializable{
	@Id
	//@GeneratedValue
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "datetime", nullable = false)
	private String datetime;

	@Column(name = "longitude", nullable = false)
	private String longitude;

	@Column(name = "latitude", nullable = false)
	private String latitude;

	@Column(name = "userid", nullable = true)
	private String userid;

	@Column(name = "velocity", nullable = true)
	private String velocity;

	@Column(name = "height", nullable = true)
	private String height;
	
	@Column(name = "para", nullable = true)
	private String para;
}
