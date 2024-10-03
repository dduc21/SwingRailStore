package com.example.demo.srt.sd77.enums;

import lombok.Getter;

@Getter
public enum StatusSanPham {

    HIEN_THI(true),
    AN(false);

    private final Boolean trangThai;

    StatusSanPham(Boolean trangThai) {
        this.trangThai = trangThai;
    }


}
