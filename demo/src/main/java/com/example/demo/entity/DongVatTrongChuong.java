package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "DongVatTrongChuong")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DongVatTrongChuong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "maChuong")
    private ChuongNuoi chuongNuoi;

    @Column(name = "loaiDongVat")
    private String loaiDongVat;

    @Column(name = "maDongVat")
    private String maDongVat;

    @Column(name = "theoCon")
    private Boolean theoCon;

    @Column(name = "soLuong")
    private Integer soLuong;

    @Column(name = "ngayNhap")
    private LocalDate ngayNhap;

    @Column(name = "nguonGoc")
    private String nguonGoc;

    @Column(name = "tinhTrang")
    private String tinhTrang;

    @Column(name = "ghiChu")
    private String ghiChu;
}
