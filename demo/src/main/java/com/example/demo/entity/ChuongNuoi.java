package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ChuongNuoi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChuongNuoi {

    @Id
    @Column(name = "maChuong")
    private String maChuong;

    @Column(name = "tenChuong")
    private String tenChuong;

    @Column(name = "loaiVatNuoi")
    private String loaiVatNuoi;

    @Column(name = "trangThai")
    private String trangThai;

    @Column(name = "ngayTao")
    private LocalDate ngayTao;

    @ManyToOne
    @JoinColumn(name = "maKhu")
    private KhuNuoi khuNuoi;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private TaiKhoan owner;

    @ManyToMany
    @JoinTable(
            name = "Chuong_NguoiQuanLy",
            joinColumns = @JoinColumn(name = "chuong_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<TaiKhoan> nguoiQuanLy;

    @Override
    public String toString() {
        return "ChuongNuoi{" +
                "maChuong='" + maChuong + '\'' +
                ", tenChuong='" + tenChuong + '\'' +
                ", loaiVatNuoi='" + loaiVatNuoi + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", ngayTao=" + ngayTao +
                '}';
    }
}
