package com.codewitharjun.fullstackmembersystem.service;

import com.codewitharjun.fullstackmembersystem.model.Comic;
import com.codewitharjun.fullstackmembersystem.model.Image;
import com.codewitharjun.fullstackmembersystem.repository.ComicRepository;
import com.codewitharjun.fullstackmembersystem.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    // Constructor injection for dependencies
    public ImageService(ComicRepository comicRepository, ImageRepository imageRepository) {
        this.comicRepository = comicRepository;
        this.imageRepository = imageRepository;
    }

    // Upload comic and its associated images
    public Comic uploadComic(String title, List<MultipartFile> files) {
        // Create a new Comic instance
        Comic comic = new Comic(title);
        comicRepository.save(comic); // Save comic to get its ID

        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            String filePath = saveImage(file, title); // Save image and get path
            if (filePath != null) { // Only add image if saved successfully
                Image image = new Image(file.getOriginalFilename(), filePath, comic); // Create Image instance
                images.add(image);
            }
        }
        comic.setImages(images);
        imageRepository.saveAll(images); // Save all images related to the comic
        return comic; // Return the saved comic
    }

    // Retrieve all comics
    public List<Comic> getAllComics() {
        return comicRepository.findAll();
    }

    // Retrieve all images for a specific comic
    public List<Image> getComicImages(Long comicId) {
        return imageRepository.findByComicId(comicId);
    }

    // Save image to disk
    private String saveImage(MultipartFile file, String comicTitle) {
        // Check if content type is null
        String contentType = file.getContentType();
        if (contentType == null || !isValidImageType(contentType)) {
            logger.error("Invalid or null file type: {}", contentType);
            return null; // Return null if invalid file type or content type is null
        }

        // Construct upload directory path
        String uploadDirPath = "C:/Users/POWER USER/OneDrive/桌面/github/fullstack-memberCRUD/uploads/" + comicTitle;
        File comicDir = new File(uploadDirPath);

        if (!comicDir.exists() && !comicDir.mkdirs()) {
            logger.warn("Failed to create directory: {}", comicDir.getAbsolutePath());
            return null; // Return null if directory creation fails
        }

        // Define the file path for the image
        String filePath = comicDir.getPath() + "/" + file.getOriginalFilename();
        try {
            // Write file to the new directory
            file.transferTo(new File(filePath));
            logger.info("Saved image to: {}", filePath);
        } catch (IOException e) {
            // Handle error
            logger.error("Error saving image: {}", e.getMessage(), e);
            return null; // Return null if file transfer fails
        }
        return filePath; // Return the saved file path
    }

    // Validate the uploaded image type
    private boolean isValidImageType(String contentType) {
        return "image/jpeg".equals(contentType) || "image/png".equals(contentType); // Extend as needed
    }
}
