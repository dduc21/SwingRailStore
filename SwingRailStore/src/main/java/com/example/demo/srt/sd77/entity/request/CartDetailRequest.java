package com.example.demo.srt.sd77.entity.request;

import com.example.demo.srt.sd77.entity.SanPhamChiTiet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailRequest {

    SanPhamChiTiet sanPhamChiTiet;

    Integer soLuong;

    Long idKhachHang;

}
