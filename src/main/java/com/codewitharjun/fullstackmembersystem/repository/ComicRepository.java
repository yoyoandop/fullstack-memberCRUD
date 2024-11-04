package com.codewitharjun.fullstackmembersystem.repository;

import com.codewitharjun.fullstackmembersystem.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComicRepository extends JpaRepository<Comic, Long> {
    // 根據標題查詢漫畫
    Optional<Comic> findByTitle(String title);
}
