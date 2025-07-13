package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ChiTietXuatKho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietXuatKho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "maPhieuXuat")
    private XuatKho xuatKho;

    @ManyToOne
    @JoinColumn(name = "maVatTu")
    private VatTu vatTu;

    @Column(name = "soLuongXuat")
    private Integer soLuongXuat;
}
