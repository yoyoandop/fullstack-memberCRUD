package com.codewitharjun.fullstackmembersystem.controller;

import com.codewitharjun.fullstackmembersystem.model.Comic;
import com.codewitharjun.fullstackmembersystem.model.Image;
import com.codewitharjun.fullstackmembersystem.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comics")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    // 上傳漫畫
    @PostMapping
    public ResponseEntity<Map<String, Object>> uploadComic(@RequestParam("title") String title,
                                                           @RequestParam("images") List<MultipartFile> files) {
        if (title == null || title.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "漫畫標題不能為空"));
        }

        if (files == null || files.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "請至少上傳一張圖片"));
        }

        try {
            Comic comic = imageService.uploadComic(title, files);
            Map<String, Object> response = new HashMap<>();
            response.put("id", comic.getId());
            response.put("title", comic.getTitle());
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("上傳漫畫時發生錯誤: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "上傳漫畫失敗: " + e.getMessage()));
        }
    }

    // 獲取所有漫畫
    @GetMapping("/all")
    public ResponseEntity<List<Comic>> getAllComics() {
        return ResponseEntity.ok(imageService.getAllComics());
    }

    // 獲取特定漫畫的所有圖片
    @GetMapping("/{comicId}/images")
    public ResponseEntity<?> getComicImages(@PathVariable Long comicId) {
        List<Image> images = imageService.getComicImages(comicId);
        if (images.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "該漫畫的圖片不存在"));
        }
        return ResponseEntity.ok(images);
    }
}
