package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Kho")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kho {

    @Id
    @Column(name = "maKho")
    private String maKho;

    @Column(name = "tenKho")
    private String tenKho;

    @Column(name = "moTa")
    private String moTa;

    @Column(name = "loaiKho")
    private String loaiKho;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private TaiKhoan owner;

    @ManyToOne(optional = true)
    @JoinColumn(name = "maKhu", referencedColumnName = "maKhu")
    private KhuNuoi khuNuoi;

    @ManyToMany
    @JoinTable(
            name = "Kho_NguoiQuanLy",
            joinColumns = @JoinColumn(name = "kho_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<TaiKhoan> nguoiQuanLy;

    @Override
    public String toString() {
        return "Kho{" +
                "maKho='" + maKho + '\'' +
                ", tenKho='" + tenKho + '\'' +
                ", moTa='" + moTa + '\'' +
                ", khuNuoi=" + (khuNuoi != null ? khuNuoi.getMaKhu() : "null") +
                '}';
    }
}
