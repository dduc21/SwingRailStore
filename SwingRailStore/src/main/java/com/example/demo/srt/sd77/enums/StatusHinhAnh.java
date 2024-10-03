package com.example.demo.srt.sd77.enums;

import lombok.Getter;

@Getter
public enum StatusHinhAnh {

    HIEN_THI(true),
    AN(false);

    private final Boolean trangThai;

    StatusHinhAnh(Boolean trangThai) {
        this.trangThai = trangThai;
    }
}
