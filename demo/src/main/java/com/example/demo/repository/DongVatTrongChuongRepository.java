package com.example.demo.repository;

import com.example.demo.entity.DongVatTrongChuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface DongVatTrongChuongRepository extends JpaRepository<DongVatTrongChuong, Integer> {

    @Query("SELECT SUM(dv.soLuong) FROM DongVatTrongChuong dv WHERE dv.chuongNuoi.maChuong = :maChuong")
    Integer sumSoLuongByMaChuong(@Param("maChuong") String maChuong);

    @Query("SELECT MIN(dv.ngayNhap) FROM DongVatTrongChuong dv WHERE dv.chuongNuoi.maChuong = :maChuong")
    LocalDate findMinNgayNhap(@Param("maChuong") String maChuong);
}
