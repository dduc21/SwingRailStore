package com.example.demo.srt.sd77.entity;

import com.example.demo.srt.sd77.entity.base.PrimaryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.Date;

@Data
@Entity
@Table(name = "khach_hang")
public class KhachHang extends PrimaryEntity {

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    @Nationalized
    private String ten;

    @Column(name = "ngay_sinh")
    private Date ngaySinh;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "email")
    private String email;

    @Column(name = "loai_khach")
    private Boolean loaiKhach;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "xa")
    @Nationalized
    private String xa;

    @Column(name = "phuong")
    @Nationalized
    private String phuong;

    @Column(name = "tinh")
    @Nationalized
    private String tinh;

    @Column(name = "dia_chi")
    @Nationalized
    private String diaChi;

    @Column(name = "ma_xa")
    private String maXa;

    @Column(name = "ma_phuong")
    private String maPhuong;

    @Column(name = "ma_tinh")
    private String maTinh;

    @Column(name = "trang_thai")
    private Boolean trangThai;
}
