package com.example.demo.srt.sd77.enums;

import lombok.Getter;

@Getter
public enum StatusHoaDon {

    CHO(0),
    HUY(1),
    DANG_VAN_CHUYEN(2),
    TRA_HANG(3),
    HOAN_THANH(4);

    private final Integer trangThai;

    StatusHoaDon(Integer trangThai) {
        this.trangThai = trangThai;
    }
}
