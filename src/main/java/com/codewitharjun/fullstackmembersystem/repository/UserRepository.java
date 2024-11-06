package com.codewitharjun.fullstackmembersystem.repository;

import com.codewitharjun.fullstackmembersystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//Query
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 使用 @Query 查詢用戶名為特定值的用戶
    @Query("SELECT u FROM User u WHERE u.username = :username")
    List<User> findByUsername(String username);
}