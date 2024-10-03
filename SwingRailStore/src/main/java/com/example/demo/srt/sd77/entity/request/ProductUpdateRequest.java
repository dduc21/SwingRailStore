package com.example.demo.srt.sd77.entity.request;

import com.example.demo.srt.sd77.entity.TheLoai;
import com.example.demo.srt.sd77.entity.ThuongHieu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateRequest {

    Long id;

    String ten;

    ThuongHieu idThuongHieu;

    TheLoai idTheLoai;

    Boolean trangThai;
}
