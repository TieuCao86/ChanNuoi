package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "XuatKho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class XuatKho {
    @Id
    @Column(name = "maPhieuXuat")
    private String maPhieuXuat;

    @Column(name = "ngayXuat")
    private LocalDate ngayXuat;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private TaiKhoan taiKhoan;

    @Column(name = "ghiChu")
    private String ghiChu;
}
