package com.example.demo.srt.sd77.enums;

import lombok.Getter;

@Getter
public enum StatusThuongHieu {

    HIEN_THI(true),
    AN(false);

    private final Boolean trangThai;

    StatusThuongHieu(Boolean trangThai) {
        this.trangThai = trangThai;
    }
}
