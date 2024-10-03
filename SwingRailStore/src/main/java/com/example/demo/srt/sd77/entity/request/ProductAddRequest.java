package com.example.demo.srt.sd77.entity.request;

import com.example.demo.srt.sd77.entity.TheLoai;
import com.example.demo.srt.sd77.entity.ThuongHieu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAddRequest {

    ThuongHieu idThuongHieu;

    TheLoai idTheLoai;

    String ten;

    String moTa;
}
