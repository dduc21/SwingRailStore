package com.example.demo.srt.sd77.enums;

import lombok.Getter;

@Getter
public enum StatusKichCo {

    HIEN_THI(true),
    AN(false);

    private final Boolean trangThai;

    StatusKichCo(Boolean trangThai) {
        this.trangThai = trangThai;
    }

}
