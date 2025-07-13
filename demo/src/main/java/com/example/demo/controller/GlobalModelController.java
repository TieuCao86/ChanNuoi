package com.example.demo.controller;

import com.example.demo.entity.Kho;
import com.example.demo.service.KhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelController {

    private final KhoService khoService;

    @ModelAttribute("khoCamList")
    public List<Kho> getKhoCamList() {
        List<Kho> list = khoService.getAll().stream()
                .filter(k -> "cám".equalsIgnoreCase(k.getLoaiKho()))
                .toList();
        System.out.println(">>> [Model] Số kho cám: " + list.size());
        return list;
    }

    @ModelAttribute("otherKhoList")
    public List<Kho> getOtherKhoList() {
        List<Kho> list = khoService.getAll().stream()
                .filter(k -> !"cám".equalsIgnoreCase(k.getLoaiKho()))
                .collect(Collectors.toMap(
                        k -> k.getLoaiKho().toLowerCase(),
                        k -> k,
                        (k1, k2) -> k1
                ))
                .values().stream().toList();
        System.out.println(">>> [Model] Số kho khác: " + list.size());
        return list;
    }

}
