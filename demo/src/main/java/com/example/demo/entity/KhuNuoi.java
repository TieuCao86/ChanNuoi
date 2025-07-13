package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "KhuNuoi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KhuNuoi {

    @Id
    @Column(name = "maKhu")
    private String maKhu;

    @Column(name = "tenKhu")
    private String tenKhu;

    @Column(name = "moTa")
    private String moTa;

    @Column(name = "trangThai")
    private String trangThai;

    @Column(name = "ngayTao")
    private LocalDate ngayTao;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private TaiKhoan owner;

    @OneToMany(mappedBy = "khuNuoi")
    private List<ChuongNuoi> danhSachChuong;

    @Override
    public String toString() {
        return "KhuNuoi{" +
                "maKhu='" + maKhu + '\'' +
                ", tenKhu='" + tenKhu + '\'' +
                ", moTa='" + moTa + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", ngayTao=" + ngayTao +
                '}';
    }
}
