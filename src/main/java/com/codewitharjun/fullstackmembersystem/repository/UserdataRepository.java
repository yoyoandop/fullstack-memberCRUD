package com.codewitharjun.fullstackmembersystem.repository;

import com.codewitharjun.fullstackmembersystem.model.Userdata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserdataRepository extends JpaRepository<Userdata, Long> {
    Userdata findByEmail(String email); // 添加此方法
}
