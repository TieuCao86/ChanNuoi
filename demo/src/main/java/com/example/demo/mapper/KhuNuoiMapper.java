package com.example.demo.mapper;

import com.example.demo.dto.KhuNuoiDTO;
import com.example.demo.entity.KhuNuoi;
import com.example.demo.repository.TaiKhoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KhuNuoiMapper implements GenericMapper<KhuNuoiDTO, KhuNuoi> {

    private final TaiKhoanRepository taiKhoanRepository;

    @Override
    public KhuNuoiDTO toDto(KhuNuoi entity) {
        KhuNuoiDTO dto = new KhuNuoiDTO();
        dto.setMaKhu(entity.getMaKhu());
        dto.setTenKhu(entity.getTenKhu());
        dto.setMoTa(entity.getMoTa());
        dto.setTrangThai(entity.getTrangThai());
        dto.setNgayTao(entity.getNgayTao());
        dto.setOwnerId(entity.getOwner() != null ? entity.getOwner().getUserId() : null);
        return dto;
    }

    @Override
    public KhuNuoi toEntity(KhuNuoiDTO dto) {
        KhuNuoi entity = new KhuNuoi();
        entity.setMaKhu(dto.getMaKhu());
        entity.setTenKhu(dto.getTenKhu());
        entity.setMoTa(dto.getMoTa());
        entity.setTrangThai(dto.getTrangThai());
        entity.setNgayTao(dto.getNgayTao());

        if (dto.getOwnerId() != null) {
            entity.setOwner(taiKhoanRepository.findById(dto.getOwnerId()).orElse(null));
        }

        return entity;
    }
}
