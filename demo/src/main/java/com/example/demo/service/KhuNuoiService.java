package com.example.demo.service;

import com.example.demo.entity.KhuNuoi;
import com.example.demo.repository.KhuNuoiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KhuNuoiService {
    private final KhuNuoiRepository repo;

    public KhuNuoi create(KhuNuoi khu) {
        khu.setMaKhu(nextMaKhu());
        khu.setNgayTao(LocalDate.now());
        return repo.save(khu);
    }

    public List<KhuNuoi> getAll() {
        return repo.findAll();
    }

    public KhuNuoi update(String maKhu, KhuNuoi khuMoi) {
        KhuNuoi khu = repo.findById(maKhu)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khu với mã: " + maKhu));

        khu.setTenKhu(khuMoi.getTenKhu());
        khu.setMoTa(khuMoi.getMoTa());
        khu.setTrangThai(khuMoi.getTrangThai());

        return repo.save(khu);
    }

    public void delete(String maKhu) {
        if (!repo.existsById(maKhu)) {
            throw new RuntimeException("Không tìm thấy khu với mã: " + maKhu);
        }
        repo.deleteById(maKhu);
    }

    private String nextMaKhu() {
        List<KhuNuoi> ds = repo.findAll();
        int max = ds.stream()
                .mapToInt(k -> Integer.parseInt(k.getMaKhu().replace("K", "")))
                .max()
                .orElse(0);
        return String.format("K%03d", max + 1);
    }
}
