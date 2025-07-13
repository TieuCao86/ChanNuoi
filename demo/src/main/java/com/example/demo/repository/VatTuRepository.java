package com.example.demo.repository;

import com.example.demo.entity.VatTu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VatTuRepository extends JpaRepository<VatTu, String> {
    List<VatTu> findByKho_MaKho(String maKho);
}

