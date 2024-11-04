package com.codewitharjun.fullstackmembersystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired; // 添加此行
import java.util.List;

import com.codewitharjun.fullstackmembersystem.model.AuthRequest; // 确保你已经更新了这个类
import com.codewitharjun.fullstackmembersystem.model.AuthResponse;
import com.codewitharjun.fullstackmembersystem.util.JwtUtil;
import com.codewitharjun.fullstackmembersystem.service.UserdataService; // 添加此行


@RestController
@CrossOrigin("http://localhost:3000") //表示只能接受來自前端端口"http://localhost:3000"的請求
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserdataService userdataService; // 使用私有變量

    // 使用構造函數注入
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserdataService userdataService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userdataService = userdataService; // 初始化
    }


    @PostMapping("/auth/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            // 使用 email password登录
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect email or password", e);
        }

        // 使用 email 獲取用戶角色
        List<String> roles = userdataService.getRolesByEmail(authRequest.email());

        // 使用 email 生成 JWT
        final String jwt = jwtUtil.generateToken(authRequest.email(),roles);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
