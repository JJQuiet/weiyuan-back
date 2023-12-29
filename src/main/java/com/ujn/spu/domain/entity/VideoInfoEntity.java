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
public class VideoInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String uploaderName;
    private String videoName;
    private String videoPath;
    private String videoDescription;
    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }
    public void setVideoName(String fileName) {
        this.videoName = fileName;
    }
    public void setVideoPath(String string) {
        this.videoPath = string;
    }
    public String getVideoPath() {
        return videoPath;
    }
    public String getVideoName() {
        return videoName;
    }
    public String getUploaderName() {
        return uploaderName;
    }
    public Long getId() {
        return id;
    }
    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }
    public String getVideoDescription() {
        return videoDescription;
    }
}
