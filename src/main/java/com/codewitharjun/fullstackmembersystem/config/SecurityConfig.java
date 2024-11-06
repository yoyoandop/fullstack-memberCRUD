package com.codewitharjun.fullstackmembersystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.codewitharjun.fullstackmembersystem.filter.JwtRequestFilter;
import com.codewitharjun.fullstackmembersystem.service.MyUserDetailsService; // 使用 MyUserDetailsService

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService userDetailsService; // 使用 MyUserDetailsService
    private final JwtRequestFilter jwtRequestFilter;

    // 构造函数注入
    public SecurityConfig(MyUserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/register").permitAll() // 允许所有用户访问注册端点
                .antMatchers("/auth/login").permitAll() // 允许所有用户访问登录端点
                .antMatchers("/users").permitAll()
                //.antMatchers("/users").hasAuthority("admin") // 只有 admin 角色的用户可以访问 /users
                .antMatchers("/user").permitAll()
                .antMatchers("/user/{id}").permitAll()
                .antMatchers("/users/username/{username}").permitAll()
                .antMatchers("/api/comics").permitAll()
                .antMatchers("/api/comics/title").permitAll()
                .antMatchers("/api/comics/images").permitAll()
                .antMatchers("/{comicId}/images").permitAll()

                .antMatchers("/api/comics/all").permitAll()
                .antMatchers("/api/comics/{id}/images").permitAll()
                .anyRequest().authenticated() // 其他请求需要身份验证
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 禁用会话管理，使用 JWT

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
