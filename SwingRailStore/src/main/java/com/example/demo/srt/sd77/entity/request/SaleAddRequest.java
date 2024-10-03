package com.example.demo.srt.sd77.entity.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SaleAddRequest {

    private String ma;

    private String ten;

    private Float phanTramGiam;

    private Date ngayBatDau;

    private Date ngayKetThuc;

    private Integer trangThai;
}
