package com.codewitharjun.fullstackmembersystem.service;

import com.codewitharjun.fullstackmembersystem.model.Comic;
import com.codewitharjun.fullstackmembersystem.model.Image;
import com.codewitharjun.fullstackmembersystem.repository.ComicRepository;
import com.codewitharjun.fullstackmembersystem.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
    private final ComicRepository comicRepository;
    private final ImageRepository imageRepository;

    @Value("${image.upload.dir}")
    private String uploadDirPath; // 這會被設置為 spring.servlet.multipart.location 中的值

    public ImageService(ComicRepository comicRepository, ImageRepository imageRepository) {
        this.comicRepository = comicRepository;
        this.imageRepository = imageRepository;
    }

    // 上傳漫畫和它的圖片
    public Comic uploadComic(String title, List<MultipartFile> files) {
        Comic comic = new Comic(title);
        comicRepository.save(comic);

        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            logger.info("正在上傳文件: {}", file.getOriginalFilename());  // 檢查是否收到文件
            String filePath = saveImage(file, title);
            if (filePath != null) {
                Image image = new Image(file.getOriginalFilename(), filePath, comic);
                images.add(image);
            }
        }
        comic.setImages(images);
        imageRepository.saveAll(images);
        return comic;
    }

    // 獲取所有漫畫
    public List<Comic> getAllComics() {
        return comicRepository.findAll();
    }

    // 根據漫畫 ID 獲取該漫畫的所有圖片
    public List<Image> getComicImages(Long comicId) {
        return imageRepository.findByComicId(comicId);
    }

    // 保存圖片到磁碟
    private String saveImage(MultipartFile file, String comicTitle) {
        String contentType = file.getContentType();
        if (contentType == null || !isValidImageType(contentType)) {
            logger.error("無效或為空的文件類型: {}", contentType);
            return null;
        }

        // 目標資料夾路徑：基於 uploadDirPath + 漫畫名稱
        String uploadDir = uploadDirPath + "/" + comicTitle;
        File comicDir = new File(uploadDir);

        // 如果資料夾不存在，則創建它
        if (!comicDir.exists()) {
            boolean dirCreated = comicDir.mkdirs();
            if (!dirCreated) {
                logger.error("無法創建資料夾: {}", comicDir.getAbsolutePath());
                return null;
            }
            logger.info("成功創建資料夾: {}", comicDir.getAbsolutePath());
        }

        // 組合完整的檔案路徑
        String filePath = comicDir.getPath() + "/" + file.getOriginalFilename();

        try {
            // 保存文件到磁碟
            file.transferTo(new File(filePath));
            logger.info("成功保存圖片到: {}", filePath);
        } catch (IOException e) {
            logger.error("保存圖片時發生錯誤: {}", e.getMessage(), e);
            return null;
        }

        return filePath;
    }

    // 檢查上傳的文件是否為有效的圖片類型
    private boolean isValidImageType(String contentType) {
        return "image/jpeg".equals(contentType) || "image/png".equals(contentType);
    }
}
