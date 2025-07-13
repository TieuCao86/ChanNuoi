package com.example.demo.repository;

import com.example.demo.entity.KhuNuoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhuNuoiRepository extends JpaRepository<KhuNuoi, String> {
    // String là kiểu của khóa chính (maKhu)
}
