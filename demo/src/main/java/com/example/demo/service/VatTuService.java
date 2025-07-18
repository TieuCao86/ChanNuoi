package com.example.demo.service;

import com.example.demo.entity.VatTu;
import com.example.demo.repository.VatTuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VatTuService {
    private final VatTuRepository vatTuRepository;

    public List<VatTu> getVatTuTrongKho(String maKho) {
        return vatTuRepository.findByKho_MaKho(maKho);
    }
}
