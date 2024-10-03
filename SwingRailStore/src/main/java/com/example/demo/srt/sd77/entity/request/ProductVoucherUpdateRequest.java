package com.example.demo.srt.sd77.entity.request;

import com.example.demo.srt.sd77.entity.HoaDon;
import com.example.demo.srt.sd77.entity.Voucher;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVoucherUpdateRequest {

    Voucher voucher;

    HoaDon hoaDon;
}
