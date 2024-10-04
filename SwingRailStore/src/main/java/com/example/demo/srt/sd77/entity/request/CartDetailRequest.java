package com.example.demo.srt.sd77.entity.request;

import com.example.demo.srt.sd77.entity.SanPhamChiTiet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailRequest {

    private SanPhamChiTiet sanPhamChiTiet;

    private Integer soLuong;

    private Long idKhachHang;

}
