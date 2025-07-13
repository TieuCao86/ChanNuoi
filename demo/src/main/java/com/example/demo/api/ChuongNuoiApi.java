package com.example.demo.api;

import com.example.demo.dto.ChuongNuoiDTO;
import com.example.demo.entity.ChuongNuoi;
import com.example.demo.mapper.ChuongNuoiMapper;
import com.example.demo.repository.ChuongNuoiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<ChuongNuoiDTO> getById(@PathVariable String id) {
        return chuongNuoiRepository.findById(id)
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

    @PutMapping("/{id}")
    public ChuongNuoiDTO update(@PathVariable String id, @RequestBody ChuongNuoiDTO dto) {
        return chuongNuoiRepository.findById(id)
                .map(existing -> {
                    ChuongNuoi updated = chuongNuoiMapper.toEntity(dto);
                    updated.setMaChuong(id); // đảm bảo đúng ID
                    return chuongNuoiMapper.toDto(chuongNuoiRepository.save(updated));
                })
                .orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        chuongNuoiRepository.deleteById(id);
    }
}
