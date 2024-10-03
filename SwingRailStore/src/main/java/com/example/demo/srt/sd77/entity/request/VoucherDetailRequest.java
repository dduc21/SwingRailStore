package com.example.demo.srt.sd77.entity.request;

import com.example.demo.srt.sd77.entity.KhachHang;
import com.example.demo.srt.sd77.entity.Voucher;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherDetailRequest {

    private Voucher voucher;

    private KhachHang khachHang;
}
