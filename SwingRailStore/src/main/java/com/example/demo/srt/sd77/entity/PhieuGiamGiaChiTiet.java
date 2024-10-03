package com.example.demo.srt.sd77.entity;

import com.example.demo.srt.sd77.entity.base.PrimaryEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "phieu_giam_gia_chi_tiet")
public class PhieuGiamGiaChiTiet extends PrimaryEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_phieu_giam_gia")
    private Voucher idPhieuGiamGia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang idKhachHang;

}
