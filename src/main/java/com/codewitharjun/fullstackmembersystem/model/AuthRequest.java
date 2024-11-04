package com.codewitharjun.fullstackmembersystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List; // 確保導入 List

public record AuthRequest(
        @JsonProperty("email") String email,
        @JsonProperty("password") String password
) {
    // 記錄類不需要顯式的構造函數，Jackson 會自動處理屬性的綁定
}
