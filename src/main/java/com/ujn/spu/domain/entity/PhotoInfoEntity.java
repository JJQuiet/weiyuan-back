package com.ujn.spu.domain.entity;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class PhotoInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String uploaderName;
    private String photoName;
    private String photoPath;
    private String photoDescription;
    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }
    public void setPhotoName(String fileName) {
        this.photoName = fileName;
    }
    public void setPhotoPath(String string) {
        this.photoPath = string;
    }
    public String getPhotoPath() {
        return photoPath;
    }
    public String getPhotoName() {
        return photoName;
    }
    public String getUploaderName() {
        return uploaderName;
    }
    public Long getId() {
        return id;
    }
    public void setPhotoDescription(String photoDescription) {
        this.photoDescription = photoDescription;
    }
    public String getPhotoDescription() {
        return photoDescription;
    }
}
