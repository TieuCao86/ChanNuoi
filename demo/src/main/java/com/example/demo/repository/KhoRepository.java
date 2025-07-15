package com.example.demo.repository;

import com.example.demo.entity.Kho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhoRepository extends JpaRepository<Kho, String> {

    List<Kho> findByLoaiKho(String loaiKho);

    List<Kho> findByKhuNuoi_MaKhu(String maKhu);
}
