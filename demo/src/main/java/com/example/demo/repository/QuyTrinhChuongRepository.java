package com.example.demo.repository;

import com.example.demo.entity.QuyTrinhChuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuyTrinhChuongRepository extends JpaRepository<QuyTrinhChuong, Integer> {

    QuyTrinhChuong findByChuongNuoi_MaChuong(String maChuong);
}
