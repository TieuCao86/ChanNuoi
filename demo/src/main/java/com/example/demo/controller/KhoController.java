package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KhoController {

    @GetMapping("/kho")
    public String viewKhoPage() {
        return "sidebar/kho :: khoContent"; // fragment riêng nếu có
    }
}
