package com.example.demo.srt.sd77.enums;

import lombok.Getter;

@Getter
public enum StatusVoucher {

    CHUA_BAT_DAU(0),
    DANG_DIEN_RA(1),
    KET_THUC(2),
    HUY(3);

    private final Integer trangThai;

    StatusVoucher(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
