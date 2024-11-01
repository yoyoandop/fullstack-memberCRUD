package com.codewitharjun.fullstackmembersystem.service;

import com.codewitharjun.fullstackmembersystem.model.Userdata;
import com.codewitharjun.fullstackmembersystem.repository.UserdataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // 引入 PasswordEncoder
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Collections;

@Service
public class UserdataService {

    private final UserdataRepository userdataRepository;
    private final PasswordEncoder passwordEncoder; // 引入 PasswordEncoder 做密碼加密

    // 使用构造函数注入
    @Autowired
    public UserdataService(UserdataRepository userdataRepository, PasswordEncoder passwordEncoder) {
        this.userdataRepository = userdataRepository;
        this.passwordEncoder = passwordEncoder; // 初始化 PasswordEncoder
    }


    public Long getUserIdByEmail(String email) {
        Userdata user = userdataRepository.findByEmail(email);
        return (user != null) ? user.getId() : null; // 返回用户 ID 或 null
    }

    public List<String> getRolesByEmail(String email) {
        Userdata user = userdataRepository.findByEmail(email);
        return (user != null) ? user.getRoles() : null; // 返回用户角色
    }


    // 注册新用户
    public String registerUser(Userdata userdata) {
        // 检查 email 是否已经存在
        if (userdataRepository.findByEmail(userdata.getEmail()) != null) {
            return "Email 已经注册";
        }

        // 如果角色为空，则分配默认角色
        if (userdata.getRoles() == null || userdata.getRoles().isEmpty()) {
            userdata.setRoles(Collections.singletonList("ROLE_USER")); // 默认角色
        }

        // 对密码进行加密
        String encodedPassword = passwordEncoder.encode(userdata.getPassword());
        userdata.setPassword(encodedPassword); // 设置加密后的密码

        // 保存新用户资料
        userdataRepository.save(userdata);
        return "注册成功";
    }
}
