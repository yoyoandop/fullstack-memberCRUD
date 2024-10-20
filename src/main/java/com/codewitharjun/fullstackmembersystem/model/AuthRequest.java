package com.codewitharjun.fullstackmembersystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthRequest(
        @JsonProperty("email") String email,
        @JsonProperty("password") String password
) {
    // 记录类不需要显式的构造函数，Jackson 会自动处理属性的绑定
}
