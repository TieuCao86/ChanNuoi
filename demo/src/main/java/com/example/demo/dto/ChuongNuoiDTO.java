package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ChuongNuoiDTO {
    private String maChuong;
    private String tenChuong;
    private String loaiVatNuoi;
    private String trangThai;
    private LocalDate ngayTao;
    private String maKhu;
    private Integer ownerId;
    private List<Integer> nguoiQuanLyIds;

    // Cần thêm:
    private String tenKhu;
    private Integer soLuongVatNuoi;
    private String ngayBatDau;
    private List<String> nhanVienPhuTrach;
    private Integer tienDo;
    private Long chiPhiThang;
    private String loTrinhText;
}

