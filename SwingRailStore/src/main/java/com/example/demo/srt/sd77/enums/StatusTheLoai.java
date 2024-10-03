package com.example.demo.srt.sd77.enums;

import lombok.Getter;

@Getter
public enum StatusTheLoai {

    HIEN_THI(true),
    AN(false);

    private final Boolean trangThai;

    StatusTheLoai(Boolean trangThai) {
        this.trangThai = trangThai;
    }

}
