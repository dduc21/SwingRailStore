package com.example.demo.srt.sd77.enums;

import lombok.Getter;

@Getter
public enum StatusMauSac {

    HIEN_THI(true),
    AN(false);

    private final Boolean trangThai;

    StatusMauSac(Boolean trangThai) {
        this.trangThai = trangThai;
    }

}
