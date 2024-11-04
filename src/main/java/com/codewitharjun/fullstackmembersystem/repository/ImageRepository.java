package com.codewitharjun.fullstackmembersystem.repository;

import com.codewitharjun.fullstackmembersystem.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByComicId(Long comicId); // 根據漫畫ID查詢所有圖片

    // 根據圖片名稱查詢
    List<Image> findByName(String name);
}
