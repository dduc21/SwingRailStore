package com.example.demo.srt.sd77.entity;

import com.example.demo.srt.sd77.entity.base.PrimaryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "san_pham_chi_tiet")
public class SanPhamChiTiet extends PrimaryEntity {

    @Column(name = "QRcode")
    private String QRcode;

    @Column(name = "so_luong_ton")
    private Integer soLuongTon;

    @Column(name = "don_gia")
    private BigDecimal donGia;

    @Column(name = "gia_tri_giam")
    private BigDecimal giaTriGiam;

    @Column(name = "trong_luong")
    private Float trongLuong;

    @Column(name = "trang_thai")
    private Boolean trangThai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_san_pham")
    private SanPham idSanPham;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mau_sac")
    private MauSac idMauSac;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_kich_co")
    private KichCo idKichCo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dot_giam_gia")
    private DotGiamGia idDotGiamGia;

}
