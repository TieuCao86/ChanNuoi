package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChuongNuoiController {

    // Hiển thị trang danh sách chuồng nuôi
    @GetMapping("/chuong-nuoi")
    public String viewChuongNuoiPage() {
        return "sidebar/chuong-nuoi :: chuongNuoiContent";
    }

    // Hiển thị chi tiết chuồng nuôi theo mã
    @GetMapping("/chuong-nuoi/{maChuong}")
    public String viewChuongNuoiDetail(@PathVariable("maChuong") String maChuong, Model model) {
        model.addAttribute("maChuong", maChuong);
        return "sidebar/chuong-nuoiDetail :: chuongDetailContent";
    }
}
