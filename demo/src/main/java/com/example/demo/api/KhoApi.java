package com.example.demo.api;

import com.example.demo.dto.KhoDTO;
import com.example.demo.entity.Kho;
import com.example.demo.service.KhoService;
import com.example.demo.mapper.KhoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/kho")
@RequiredArgsConstructor
public class KhoApi {

    private final KhoService khoService;

    // Lấy danh sách tất cả kho dưới dạng DTO
    @GetMapping
    public ResponseEntity<List<KhoDTO>> getAll() {
        List<KhoDTO> result = khoService.getAll().stream()
                .map(KhoMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // ✅ Thêm: Lấy kho theo mã
    @GetMapping("/{maKho}")
    public ResponseEntity<KhoDTO> getByMaKho(@PathVariable String maKho) {
        Kho kho = khoService.findByMaKho(maKho);
        return ResponseEntity.ok(KhoMapper.toDto(kho));
    }

    // Thêm kho mới
    @PostMapping
    public ResponseEntity<KhoDTO> create(@RequestBody Kho kho) {
        Kho created = khoService.create(kho);
        return ResponseEntity.ok(KhoMapper.toDto(created));
    }

    // Cập nhật kho
    @PutMapping("/{maKho}")
    public ResponseEntity<KhoDTO> update(@PathVariable String maKho, @RequestBody Kho khoMoi) {
        Kho updated = khoService.update(maKho, khoMoi);
        return ResponseEntity.ok(KhoMapper.toDto(updated));
    }

    // Xoá kho
    @DeleteMapping("/{maKho}")
    public ResponseEntity<Void> delete(@PathVariable String maKho) {
        khoService.delete(maKho);
        return ResponseEntity.noContent().build();
    }
}
