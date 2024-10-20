package com.codewitharjun.fullstackmembersystem.service;

import com.codewitharjun.fullstackmembersystem.model.Userdata;
import com.codewitharjun.fullstackmembersystem.repository.UserdataRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserdataRepository userdataRepository;

    // 使用构造函数注入
    public MyUserDetailsService(UserdataRepository userdataRepository) {
        this.userdataRepository = userdataRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Userdata user = userdataRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        // 假设你的 Userdata 有一个方法可以返回用户的角色
        // 这里假设角色是字符串类型，可以根据实际情况调整
        authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // 这里可以根据实际角色填充

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
