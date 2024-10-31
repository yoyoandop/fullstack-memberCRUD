package com.codewitharjun.fullstackmembersystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codewitharjun.fullstackmembersystem.model.AuthRequest; // 确保你已经更新了这个类
import com.codewitharjun.fullstackmembersystem.model.AuthResponse;
import com.codewitharjun.fullstackmembersystem.util.JwtUtil;

@RestController
@CrossOrigin("http://localhost:3000") //表示只能接受來自前端端口"http://localhost:3000"的請求
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            // 使用 email 登录
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect email or password", e);
        }

        // 使用 email 生成 JWT
        final String jwt = jwtUtil.generateToken(authRequest.email());

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
