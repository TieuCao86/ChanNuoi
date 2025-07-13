package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "NhapKho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhapKho {
    @Id
    @Column(name = "maPhieuNhap")
    private String maPhieuNhap;

    @Column(name = "ngayNhap")
    private LocalDate ngayNhap;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private TaiKhoan taiKhoan;

    @Column(name = "ghiChu")
    private String ghiChu;
}
