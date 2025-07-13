package com.example.demo.repository;

import com.example.demo.entity.Kho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KhoRepository extends JpaRepository<Kho, String> {
    List<Kho> findByLoaiKho(String loaiKho);

}
