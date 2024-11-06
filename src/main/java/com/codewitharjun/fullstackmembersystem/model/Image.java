package com.codewitharjun.fullstackmembersystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference; // 引入這個包
import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String fileName;
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "comic_id")
    @JsonBackReference // 加入這個註解
    private Comic comic; // 關聯的漫畫

    public Image() {
    }

    public Image(String fileName, String filePath, Comic comic) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.comic = comic; // 正確設置comic
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }
}
