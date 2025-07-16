package com.example.demo.service;

import com.example.demo.entity.Kho;
import com.example.demo.repository.KhoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KhoService {

    private final KhoRepository repo;

    public List<Kho> getAll() {
        return repo.findAll();
    }

    public Kho create(Kho kho) {
        kho.setMaKho(nextMaKho());
        return repo.save(kho);
    }

    public Kho update(String maKho, Kho khoMoi) {
        Kho kho = repo.findById(maKho)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kho với mã: " + maKho));

        kho.setTenKho(khoMoi.getTenKho());
        kho.setMoTa(khoMoi.getMoTa());
        kho.setLoaiKho(khoMoi.getLoaiKho());
        kho.setKhuNuoi(khoMoi.getKhuNuoi());
        kho.setOwner(khoMoi.getOwner());
        kho.setNguoiQuanLy(khoMoi.getNguoiQuanLy());

        return repo.save(kho);
    }

    public void delete(String maKho) {
        if (!repo.existsById(maKho)) {
            throw new RuntimeException("Không tìm thấy kho với mã: " + maKho);
        }
        repo.deleteById(maKho);
    }

    public Kho findByMaKho(String maKho) {
        return repo.findById(maKho)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kho với mã: " + maKho));
    }

    private String nextMaKho() {
        List<Kho> ds = repo.findAll();
        int max = ds.stream()
                .mapToInt(k -> {
                    try {
                        return Integer.parseInt(k.getMaKho().replace("KHO", ""));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);
        return String.format("KHO%03d", max + 1);
    }
}
