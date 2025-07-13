package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "VatTu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VatTu {
    @Id
    @Column(name = "maVatTu")
    private String maVatTu;

    @Column(name = "tenVatTu")
    private String tenVatTu;

    @Column(name = "loaiVatTu")
    private String loaiVatTu;

    @Column(name = "soLuong")
    private Integer soLuong;

    @Column(name = "donVi")
    private String donVi;

    @Column(name = "ngayNhap")
    private LocalDate ngayNhap;

    @Column(name = "hanSuDung")
    private LocalDate hanSuDung;

    @ManyToOne
    @JoinColumn(name = "maKho")
    private Kho kho;
}

