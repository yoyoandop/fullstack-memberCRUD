package com.codewitharjun.fullstackmembersystem.controller;

import com.codewitharjun.fullstackmembersystem.model.Userdata;
import com.codewitharjun.fullstackmembersystem.service.UserdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserdataController {

    @Autowired
    private UserdataService userdataService;

    // POST 請求來註冊新用戶
    @PostMapping("/register")
    public String registerUser(@RequestBody Userdata userdata) {
        return userdataService.registerUser(userdata);
    }
}
