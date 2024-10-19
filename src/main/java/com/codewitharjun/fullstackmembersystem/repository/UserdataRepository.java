package com.codewitharjun.fullstackmembersystem.repository;

import com.codewitharjun.fullstackmembersystem.model.Userdata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserdataRepository extends JpaRepository<Userdata, Long> {
    // 可以根據 email 查詢用戶是否存在
    Userdata findByEmail(String email);
}
