package com.codewitharjun.fullstackmembersystem.service;

import com.codewitharjun.fullstackmembersystem.model.Userdata;
import com.codewitharjun.fullstackmembersystem.repository.UserdataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserdataService {

    @Autowired
    private UserdataRepository userdataRepository;

    // 註冊新用戶
    public String registerUser(Userdata userdata) {
        // 檢查 email 是否已經存在
        if (userdataRepository.findByEmail(userdata.getEmail()) != null) {
            return "Email 已經註冊";
        }
        // 保存新用戶資料
        userdataRepository.save(userdata);
        return "註冊成功";
    }
}
