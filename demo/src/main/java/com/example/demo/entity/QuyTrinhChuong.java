package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "QuyTrinhChuong")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuyTrinhChuong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maQuyTrinh;

    @ManyToOne
    @JoinColumn(name = "maChuong")
    private ChuongNuoi chuongNuoi;

    @Column(name = "ghiChu")
    private String ghiChu;
}
