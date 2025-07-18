package com.example.demo.controller;

import com.example.demo.entity.VatTu;
import com.example.demo.service.VatTuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VatTuController {

    private final VatTuService vatTuService;

    @GetMapping("/kho/{maKho}/vattu")
    public String hienThiVatTuTrongKho(@PathVariable String maKho, Model model) {
        List<VatTu> danhSach = vatTuService.getVatTuTrongKho(maKho);
        model.addAttribute("vatTus", danhSach);
        model.addAttribute("maKho", maKho);
        return "vattu_trong_kho"; // Tên file HTML
    }
}

