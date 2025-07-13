package com.example.demo.mapper;

import com.example.demo.dto.TaiKhoanDTO;
import com.example.demo.entity.TaiKhoan;
import org.springframework.stereotype.Component;

@Component
public class TaiKhoanMapper implements GenericMapper<TaiKhoanDTO, TaiKhoan> {

    @Override
    public TaiKhoanDTO toDto(TaiKhoan entity) {
        TaiKhoanDTO dto = new TaiKhoanDTO();
        dto.setUserId(entity.getUserId());
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setSoDienThoai(entity.getSoDienThoai());
        dto.setIsOwner(entity.getIsOwner());
        return dto;
    }

    @Override
    public TaiKhoan toEntity(TaiKhoanDTO dto) {
        TaiKhoan entity = new TaiKhoan();
        entity.setUserId(dto.getUserId());
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setSoDienThoai(dto.getSoDienThoai());
        entity.setIsOwner(dto.getIsOwner());
        return entity;
    }
}
