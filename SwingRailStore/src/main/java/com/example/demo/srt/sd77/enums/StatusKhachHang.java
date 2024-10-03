package com.example.demo.srt.sd77.enums;

import lombok.Getter;

@Getter
public enum StatusKhachHang {

    DANG_HOAT_DONG(true),
    KHONG_HOAT_DONG(false);

    private final Boolean trangThai;

    StatusKhachHang(Boolean trangThai) {
        this.trangThai = trangThai;
    }
}
