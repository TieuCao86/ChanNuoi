package com.example.demo.api;

import com.example.demo.dto.KhuNuoiDTO;
import com.example.demo.entity.KhuNuoi;
import com.example.demo.mapper.KhuNuoiMapper;
import com.example.demo.repository.KhuNuoiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khu-nuoi")
@RequiredArgsConstructor
public class KhuNuoiApi {

    private final KhuNuoiRepository khuNuoiRepository;
    private final KhuNuoiMapper khuNuoiMapper;

    @GetMapping
    public List<KhuNuoiDTO> getAll() {
        return khuNuoiRepository.findAll().stream()
                .map(khuNuoiMapper::toDto)
                .toList();
    }

    @GetMapping("/{maKhu}")
    public KhuNuoiDTO getById(@PathVariable String maKhu) {
        return khuNuoiRepository.findById(maKhu)
                .map(khuNuoiMapper::toDto)
                .orElse(null);
    }

    @PostMapping
    public KhuNuoiDTO create(@RequestBody KhuNuoiDTO dto) {
        KhuNuoi entity = khuNuoiMapper.toEntity(dto);
        return khuNuoiMapper.toDto(khuNuoiRepository.save(entity));
    }

    @PutMapping("/{maKhu}")
    public KhuNuoiDTO update(@PathVariable String maKhu, @RequestBody KhuNuoiDTO dto) {
        return khuNuoiRepository.findById(maKhu)
                .map(existing -> {
                    KhuNuoi updated = khuNuoiMapper.toEntity(dto);
                    updated.setMaKhu(maKhu); // Giữ nguyên mã khu
                    return khuNuoiMapper.toDto(khuNuoiRepository.save(updated));
                })
                .orElse(null);
    }

    @DeleteMapping("/{maKhu}")
    public void delete(@PathVariable String maKhu) {
        khuNuoiRepository.deleteById(maKhu);
    }
}
