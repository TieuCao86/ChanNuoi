package com.example.demo.api;

import com.example.demo.dto.ChuongNuoiDTO;
import com.example.demo.entity.ChuongNuoi;
import com.example.demo.mapper.ChuongNuoiMapper;
import com.example.demo.repository.ChuongNuoiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/chuong-nuoi")
@RequiredArgsConstructor
public class ChuongNuoiApi {

    private final ChuongNuoiRepository chuongNuoiRepository;
    private final ChuongNuoiMapper chuongNuoiMapper;

    @GetMapping
    public List<ChuongNuoiDTO> getAll() {
        return chuongNuoiRepository.findAll().stream()
                .map(chuongNuoiMapper::toDto)
                .toList();
    }

    @GetMapping("/{maChuong}")
    public ResponseEntity<ChuongNuoiDTO> getByMaChuong(@PathVariable String maChuong) {
        return chuongNuoiRepository.findById(maChuong)
                .map(chuongNuoiMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-khu/{maKhu}")
    public List<ChuongNuoiDTO> getByMaKhu(@PathVariable String maKhu) {
        return chuongNuoiRepository.findByKhuNuoi_MaKhu(maKhu).stream()
                .map(chuongNuoiMapper::toDto)
                .toList();
    }

    @PostMapping
    public ChuongNuoiDTO create(@RequestBody ChuongNuoiDTO dto) {
        ChuongNuoi saved = chuongNuoiRepository.save(chuongNuoiMapper.toEntity(dto));
        return chuongNuoiMapper.toDto(saved);
    }

    @PutMapping("/{maChuong}")
    public ChuongNuoiDTO update(@PathVariable String maChuong, @RequestBody ChuongNuoiDTO dto) {
        return chuongNuoiRepository.findById(maChuong)
                .map(existing -> {
                    ChuongNuoi updated = chuongNuoiMapper.toEntity(dto);
                    updated.setMaChuong(maChuong); // đảm bảo đúng mã chuồng
                    return chuongNuoiMapper.toDto(chuongNuoiRepository.save(updated));
                })
                .orElse(null);
    }

    @DeleteMapping("/{maChuong}")
    public void delete(@PathVariable String maChuong) {
        chuongNuoiRepository.deleteById(maChuong);
    }

    @GetMapping("/filter")
    public List<ChuongNuoiDTO> filter(
            @RequestParam(required = false) String maChuong,
            @RequestParam(required = false) String tenChuong,
            @RequestParam(required = false) String loaiVatNuoi,
            @RequestParam(required = false) Integer sucChua,
            @RequestParam(required = false) String trangThai,
            @RequestParam(required = false) LocalDate ngayTao // Định dạng: yyyy-MM-dd
    ) {
        return chuongNuoiRepository.filter(maChuong, tenChuong, loaiVatNuoi, sucChua, trangThai, ngayTao).stream()
                .map(chuongNuoiMapper::toDto)
                .toList();
    }
}
