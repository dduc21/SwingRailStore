package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.PhieuGiamGiaChiTiet;
import com.example.demo.srt.sd77.entity.request.VoucherDetailRequest;

import java.util.ArrayList;

public interface IPhieuGiamGiaChiTietService {
    PhieuGiamGiaChiTiet addVoucherDetail(VoucherDetailRequest voucherDetailRequest);
    ArrayList<PhieuGiamGiaChiTiet> getAllVoucherDetail(Long idPhieuGiamGia);
    ArrayList<PhieuGiamGiaChiTiet> getAllVoucherDetailByIdCustomer(Long idKhachHang);
    void deleteVoucherDetail(Long id);
}
