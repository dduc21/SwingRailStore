package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.HinhThucThanhToan;
import com.example.demo.srt.sd77.entity.request.PaymentMethodAddRequest;

import java.util.ArrayList;

public interface IHinhThucThanhToanService {
    ArrayList<HinhThucThanhToan> getAllPaymentMethodByIdBill(Long id);
    HinhThucThanhToan addPaymentMethod(PaymentMethodAddRequest req);
}
