package com.example.demo.mapper;

import com.example.demo.dto.ChuongNuoiDTO;
import com.example.demo.entity.ChuongNuoi;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ChuongNuoiMapper implements GenericMapper<ChuongNuoiDTO, ChuongNuoi> {

    private final KhuNuoiRepository khuNuoiRepository;
    private final TaiKhoanRepository taiKhoanRepository;
    private final DongVatTrongChuongRepository dongVatRepo;
    private final QuyTrinhChuongRepository quyTrinhRepo;
    private final ChiTietQuyTrinhRepository chiTietRepo;

    @Override
    public ChuongNuoiDTO toDto(ChuongNuoi entity) {
        ChuongNuoiDTO dto = new ChuongNuoiDTO();
        dto.setMaChuong(entity.getMaChuong());
        dto.setTenChuong(entity.getTenChuong());
        dto.setLoaiVatNuoi(entity.getLoaiVatNuoi());
        dto.setTrangThai(entity.getTrangThai());
        dto.setNgayTao(entity.getNgayTao());

        // Khu
        if (entity.getKhuNuoi() != null) {
            dto.setMaKhu(entity.getKhuNuoi().getMaKhu());
            dto.setTenKhu(entity.getKhuNuoi().getTenKhu());
        }

        // Chủ sở hữu
        if (entity.getOwner() != null) {
            dto.setOwnerId(entity.getOwner().getUserId());
        }

        // Người quản lý
        if (entity.getNguoiQuanLy() != null) {
            dto.setNguoiQuanLyIds(
                    entity.getNguoiQuanLy().stream()
                            .map(TaiKhoan::getUserId)
                            .toList()
            );
            dto.setNhanVienPhuTrach(
                    entity.getNguoiQuanLy().stream()
                            .map(TaiKhoan::getFullName)
                            .toList()
            );
        }

        // Số lượng vật nuôi
        Integer soLuong = dongVatRepo.sumSoLuongByMaChuong(entity.getMaChuong());
        dto.setSoLuongVatNuoi(soLuong != null ? soLuong : 0);

        // Ngày bắt đầu
        LocalDate ngayMin = dongVatRepo.findMinNgayNhap(entity.getMaChuong());
        dto.setNgayBatDau(ngayMin != null ? ngayMin.toString() : "---");

        // Tiến độ quy trình
        var quyTrinh = quyTrinhRepo.findByChuongNuoi_MaChuong(entity.getMaChuong());
        if (quyTrinh != null) {
            dto.setLoTrinhText(quyTrinh.getGhiChu());

            int tong = chiTietRepo.countByQuyTrinhChuong_MaQuyTrinh(quyTrinh.getMaQuyTrinh());
            int hoanThanh = chiTietRepo.countByQuyTrinhChuong_MaQuyTrinhAndDaHoanThanhTrue(quyTrinh.getMaQuyTrinh());
            int tienDo = (tong > 0) ? (int) ((hoanThanh * 100.0) / tong) : 0;
            dto.setTienDo(tienDo);
        }

        return dto;
    }

    @Override
    public ChuongNuoi toEntity(ChuongNuoiDTO dto) {
        ChuongNuoi entity = new ChuongNuoi();
        entity.setMaChuong(dto.getMaChuong());
        entity.setTenChuong(dto.getTenChuong());
        entity.setLoaiVatNuoi(dto.getLoaiVatNuoi());
        entity.setTrangThai(dto.getTrangThai());
        entity.setNgayTao(dto.getNgayTao());

        if (dto.getMaKhu() != null) {
            entity.setKhuNuoi(khuNuoiRepository.findById(dto.getMaKhu()).orElse(null));
        }

        if (dto.getOwnerId() != null) {
            entity.setOwner(taiKhoanRepository.findById(dto.getOwnerId()).orElse(null));
        }

        if (dto.getNguoiQuanLyIds() != null) {
            var list = dto.getNguoiQuanLyIds().stream()
                    .map(id -> taiKhoanRepository.findById(id).orElse(null))
                    .filter(tk -> tk != null)
                    .toList();
            entity.setNguoiQuanLy(list);
        }

        return entity;
    }

}
