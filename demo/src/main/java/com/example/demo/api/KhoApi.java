package com.example.demo.api;

import com.example.demo.entity.Kho;
import com.example.demo.service.KhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kho")
@RequiredArgsConstructor
public class KhoApi {

    private final KhoService khoService;

    @GetMapping
    public List<Kho> getAll() {
        return khoService.getAll();
    }

    @PostMapping
    public Kho create(@RequestBody Kho kho) {
        return khoService.create(kho);
    }

    @PutMapping("/{maKho}")
    public Kho update(@PathVariable String maKho, @RequestBody Kho khoMoi) {
        return khoService.update(maKho, khoMoi);
    }

    @DeleteMapping("/{maKho}")
    public void delete(@PathVariable String maKho) {
        khoService.delete(maKho);
    }
}
