package com.example.demo.dto;

import lombok.Data;

@Data
public class KhoDTO {
    private String maKho;
    private String tenKho;
    private String moTa;
    private String loaiKho;
    private TaiKhoanDTO owner;
}
