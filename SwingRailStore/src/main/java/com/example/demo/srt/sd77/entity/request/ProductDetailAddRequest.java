package com.example.demo.srt.sd77.entity.request;

import com.example.demo.srt.sd77.entity.KichCo;
import com.example.demo.srt.sd77.entity.MauSac;
import com.example.demo.srt.sd77.entity.SanPham;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDetailAddRequest {

    private String QRcode;

    private Integer soLuongTon;

    private BigDecimal donGia;

    private BigDecimal giaTriGiam;

    private Float trongLuong;

    private Boolean trangThai;

    private SanPham idSanPham;

    private MauSac idMauSac;

    private KichCo idKichCo;

}
