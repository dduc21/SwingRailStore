package com.example.demo.srt.sd77.entity.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class VoucherAddRequest {

    private Long id;

    private String ma;

    private String ten;

    private Float phanTramGiam;

    private BigDecimal giaTriToiThieu;

    private BigDecimal giaTriToiDa;

    private Integer soLanDung;

    private Date ngayBatDau;

    private Date ngayKetThuc;

    private Integer loaiVoucher;

    private Integer trangThai;
}
