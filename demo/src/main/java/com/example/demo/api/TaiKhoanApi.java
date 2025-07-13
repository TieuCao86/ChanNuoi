package com.example.demo.api;

import com.example.demo.dto.TaiKhoanDTO;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.mapper.TaiKhoanMapper;
import com.example.demo.repository.TaiKhoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tai-khoan")
@RequiredArgsConstructor
public class TaiKhoanApi {

    private final TaiKhoanRepository taiKhoanRepository;
    private final TaiKhoanMapper taiKhoanMapper;

    @GetMapping
    public List<TaiKhoanDTO> getAll() {
        return taiKhoanRepository.findAll().stream()
                .map(taiKhoanMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public TaiKhoanDTO getById(@PathVariable Integer id) {
        return taiKhoanRepository.findById(id)
                .map(taiKhoanMapper::toDto)
                .orElse(null);
    }

    @PostMapping
    public TaiKhoanDTO create(@RequestBody TaiKhoanDTO dto) {
        TaiKhoan saved = taiKhoanRepository.save(taiKhoanMapper.toEntity(dto));
        return taiKhoanMapper.toDto(saved);
    }

    @PutMapping("/{id}")
    public TaiKhoanDTO update(@PathVariable Integer id, @RequestBody TaiKhoanDTO dto) {
        return taiKhoanRepository.findById(id)
                .map(existing -> {
                    TaiKhoan updated = taiKhoanMapper.toEntity(dto);
                    updated.setUserId(id);
                    return taiKhoanMapper.toDto(taiKhoanRepository.save(updated));
                })
                .orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        taiKhoanRepository.deleteById(id);
    }
}
