package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ChiTietQuyTrinh")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietQuyTrinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "maQuyTrinh")
    private QuyTrinhChuong quyTrinhChuong;

    @Column(name = "ngayThu", nullable = false)
    private Integer ngayThu;

    @Column(name = "noiDungCongViec")
    private String noiDungCongViec;

    @Column(name = "daHoanThanh")
    private Boolean daHoanThanh;

    @Column(name = "coBatThuong")
    private Boolean coBatThuong;

    @Column(name = "ghiChu")
    private String ghiChu;

    @Column(name = "hinhAnh")
    private String hinhAnh;
}
