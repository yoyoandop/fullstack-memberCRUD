package com.codewitharjun.fullstackmembersystem.repository;

import com.codewitharjun.fullstackmembersystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}