package com.codewitharjun.fullstackmembersystem.controller;

import com.codewitharjun.fullstackmembersystem.model.Userdata;
import com.codewitharjun.fullstackmembersystem.service.UserdataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@CrossOrigin("http://localhost:3000") //表示只能接受來自前端端口"http://localhost:3000"的請求
public class UserdataController {

    private final UserdataService userdataService;

    // 使用构造函数注入
    public UserdataController(UserdataService userdataService) {
        this.userdataService = userdataService;
    }

    // POST 请求来注册新用户
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Userdata userdata) {
        // 确保 userdata 对象中包含 email、password 和 roles
        if (userdata.getRoles() == null || userdata.getRoles().isEmpty()) {
            return ResponseEntity.badRequest().body("Roles must not be empty.");
        }

        String responseMessage = userdataService.registerUser(userdata);
        return ResponseEntity.ok(responseMessage);
    }
}
