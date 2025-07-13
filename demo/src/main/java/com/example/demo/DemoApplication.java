package com.example.demo;

import com.example.demo.entity.KhuNuoi;
import com.example.demo.repository.KhuNuoiRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("✅ Ứng dụng Spring Boot đã khởi chạy thành công!");
	}
	@Bean
	public CommandLineRunner testData(KhuNuoiRepository khuNuoiRepository) {
		return args -> {
			List<KhuNuoi> danhSach = khuNuoiRepository.findAll();
			System.out.println("📋 Danh sách khu nuôi từ DB:");
			danhSach.forEach(System.out::println);
		};
	}
}

