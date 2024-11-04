package com.codewitharjun.fullstackmembersystem.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 漫畫書名

    @OneToMany(mappedBy = "comic", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>(); // 漫畫的所有圖片

    public Comic() {
    }

    public Comic(String title) {
        this.title = title;
        this.images = new ArrayList<>(); // 確保images列表初始化
    }

    public Comic(String title, List<Image> images) {
        this.title = title;
        this.images = images;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
