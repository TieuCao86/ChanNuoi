package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PhanQuyen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhanQuyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private TaiKhoan taiKhoan;

    @Column(name = "loaiDoiTuong")
    private String loaiDoiTuong;

    @Column(name = "doiTuongId")
    private String doiTuongId;
}
