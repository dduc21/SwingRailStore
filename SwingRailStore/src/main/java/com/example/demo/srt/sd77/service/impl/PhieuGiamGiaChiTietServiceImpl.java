package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.PhieuGiamGiaChiTiet;
import com.example.demo.srt.sd77.entity.request.VoucherDetailRequest;
import com.example.demo.srt.sd77.repository.PhieuGiamGiaChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PhieuGiamGiaChiTietServiceImpl {

    @Autowired
    private PhieuGiamGiaChiTietRepository phieuGiamGiaChiTietRepository;

    public PhieuGiamGiaChiTiet addVoucherDetail(VoucherDetailRequest voucherDetailRequest){
        PhieuGiamGiaChiTiet voucherDetail = new PhieuGiamGiaChiTiet();
        voucherDetail.setIdPhieuGiamGia(voucherDetailRequest.getVoucher());
        voucherDetail.setIdKhachHang(voucherDetailRequest.getKhachHang());
        return phieuGiamGiaChiTietRepository.save(voucherDetail);
    }

    public ArrayList<PhieuGiamGiaChiTiet> getAllVoucherDetail(Long idPhieuGiamGia){
        return (ArrayList<PhieuGiamGiaChiTiet>) phieuGiamGiaChiTietRepository.getChiTietByPhieuGiamGia(idPhieuGiamGia);
    }

    public ArrayList<PhieuGiamGiaChiTiet> getAllVoucherDetailByIdCustomer(Long idKhachHang){
        return phieuGiamGiaChiTietRepository.getChiTietByPhieuGiamGiaByIdKhachHang(idKhachHang);
    }

    public void deleteVoucherDetail(Long id){
        phieuGiamGiaChiTietRepository.deleteById(id);
    }
}
