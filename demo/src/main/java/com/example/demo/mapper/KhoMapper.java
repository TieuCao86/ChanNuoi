package com.example.demo.mapper;

import com.example.demo.dto.KhoDTO;
import com.example.demo.dto.TaiKhoanDTO;
import com.example.demo.entity.Kho;
import com.example.demo.entity.TaiKhoan;

public class KhoMapper {

    public static KhoDTO toDto(Kho kho) {
        if (kho == null) return null;

        KhoDTO dto = new KhoDTO();
        dto.setMaKho(kho.getMaKho());
        dto.setTenKho(kho.getTenKho());
        dto.setMoTa(kho.getMoTa());
        dto.setLoaiKho(kho.getLoaiKho());

        dto.setOwner(toTaiKhoanDto(kho.getOwner()));

        return dto;
    }

    public static TaiKhoanDTO toTaiKhoanDto(TaiKhoan taiKhoan) {
        if (taiKhoan == null) return null;

        TaiKhoanDTO dto = new TaiKhoanDTO();
        dto.setUserId(taiKhoan.getUserId());
        dto.setFullName(taiKhoan.getFullName());
        dto.setEmail(taiKhoan.getEmail());
        dto.setIsOwner(taiKhoan.getIsOwner());
        return dto;
    }
}
