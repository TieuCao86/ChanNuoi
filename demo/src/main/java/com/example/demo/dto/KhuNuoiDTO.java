package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class KhuNuoiDTO {
    private String maKhu;
    private String tenKhu;
    private String moTa;
    private String trangThai;
    private LocalDate ngayTao;
    private Integer ownerId;
}

