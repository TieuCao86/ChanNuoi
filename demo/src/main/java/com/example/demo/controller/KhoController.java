package com.example.demo.controller;

import com.example.demo.entity.Kho;
import com.example.demo.service.KhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class KhoController {

    private final KhoService khoService;

    @GetMapping("/kho/{maKho}")
    public String showKhoDetail(@PathVariable String maKho, Model model) {
        Kho kho = khoService.findByMaKho(maKho);
        model.addAttribute("kho", kho);
        return "fragments/kho-detail :: khoDetail";
    }
}
