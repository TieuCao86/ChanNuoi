package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "TaiKhoan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "soDienThoai", unique = true)
    private String soDienThoai;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_owner")
    private Boolean isOwner = false;

    @OneToMany(mappedBy = "owner")
    private List<ChuongNuoi> chuongDangQuanLyOwner;

    @ManyToMany(mappedBy = "nguoiQuanLy")
    private List<ChuongNuoi> chuongDangQuanLy;

    @ManyToMany(mappedBy = "nguoiQuanLy")
    private List<Kho> khoDangQuanLy;

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", password='" + password + '\'' +
                ", isOwner=" + isOwner +
                '}';
    }
}
