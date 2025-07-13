package com.example.demo.repository;

import com.example.demo.entity.ChiTietQuyTrinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietQuyTrinhRepository extends JpaRepository<ChiTietQuyTrinh, Integer> {

    int countByQuyTrinhChuong_MaQuyTrinh(int maQuyTrinh);

    int countByQuyTrinhChuong_MaQuyTrinhAndDaHoanThanhTrue(int maQuyTrinh);
}

