package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KhuNuoiController {

    @GetMapping("/khu-nuoi")
    public String viewKhuNuoiPage() {
        return "sidebar/khu-nuoi :: khuNuoiContent";
    }
}

