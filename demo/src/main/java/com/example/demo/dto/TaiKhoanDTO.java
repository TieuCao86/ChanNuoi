package com.example.demo.dto;

import lombok.Data;

@Data
public class TaiKhoanDTO {
    private Integer userId;
    private String fullName;
    private String email;
    private String soDienThoai;
    private Boolean isOwner;
}

