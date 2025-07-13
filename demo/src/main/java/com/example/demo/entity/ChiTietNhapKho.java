package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ChiTietNhapKho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietNhapKho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "maPhieuNhap")
    private NhapKho nhapKho;

    @ManyToOne
    @JoinColumn(name = "maVatTu")
    private VatTu vatTu;

    @Column(name = "soLuongNhap")
    private Integer soLuongNhap;
}

