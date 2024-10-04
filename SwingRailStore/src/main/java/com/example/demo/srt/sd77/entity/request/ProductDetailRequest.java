package com.example.demo.srt.sd77.entity.request;

import com.example.demo.srt.sd77.entity.HoaDon;
import com.example.demo.srt.sd77.entity.SanPhamChiTiet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDetailRequest {

    private HoaDon hoaDon;

    private SanPhamChiTiet sanPhamChiTiet;

    private Integer soLuong;

}
