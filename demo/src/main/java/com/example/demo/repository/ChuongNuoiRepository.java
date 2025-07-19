package com.example.demo.repository;

import com.example.demo.entity.ChuongNuoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChuongNuoiRepository extends JpaRepository<ChuongNuoi, String> {
    List<ChuongNuoi> findByKhuNuoi_MaKhu(String maKhu);

    @Query("SELECT c FROM ChuongNuoi c " +
            "WHERE (:maChuong IS NULL OR c.maChuong LIKE %:maChuong%) " +
            "AND (:tenChuong IS NULL OR c.tenChuong LIKE %:tenChuong%) " +
            "AND (:loaiVatNuoi IS NULL OR c.loaiVatNuoi = :loaiVatNuoi) " +
            "AND (:trangThai IS NULL OR c.trangThai = :trangThai) " +
            "AND (:ngayTao IS NULL OR c.ngayTao = :ngayTao)")
    List<ChuongNuoi> filter(@Param("maChuong") String maChuong,
                            @Param("tenChuong") String tenChuong,
                            @Param("loaiVatNuoi") String loaiVatNuoi,
                            @Param("sucChua") Integer sucChua,
                            @Param("trangThai") String trangThai,
                            @Param("ngayTao") LocalDate ngayTao); // kiểu LocalDate, không phải String


}
