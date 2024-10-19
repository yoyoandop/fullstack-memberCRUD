package com.codewitharjun.fullstackmembersystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 禁用CSRF保護
                .authorizeRequests()
                .antMatchers("/api/v1/register").permitAll() // 允許訪問註冊端點
                .antMatchers("/users").permitAll() // 允許訪問User這個model 做查詢
                .antMatchers("/user").permitAll() // 允許訪問User這個model 做查詢
                .antMatchers("/user/{id}").permitAll() // 允許訪問User這個model 做查詢
                .anyRequest().authenticated() // 其他請求需要身份驗證
                .and()
                .httpBasic(); // 使用基本身份驗證
    }
}
