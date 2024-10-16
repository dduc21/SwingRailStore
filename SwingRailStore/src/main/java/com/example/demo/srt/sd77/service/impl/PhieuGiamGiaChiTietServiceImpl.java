package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.PhieuGiamGiaChiTiet;
import com.example.demo.srt.sd77.entity.request.VoucherDetailRequest;
import com.example.demo.srt.sd77.repository.PhieuGiamGiaChiTietRepository;
import com.example.demo.srt.sd77.service.IPhieuGiamGiaChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PhieuGiamGiaChiTietServiceImpl implements IPhieuGiamGiaChiTietService {

    @Autowired
    private PhieuGiamGiaChiTietRepository phieuGiamGiaChiTietRepository;

    @Override
    public PhieuGiamGiaChiTiet addVoucherDetail(VoucherDetailRequest voucherDetailRequest){
        PhieuGiamGiaChiTiet voucherDetail = new PhieuGiamGiaChiTiet();
        voucherDetail.setIdPhieuGiamGia(voucherDetailRequest.getVoucher());
        voucherDetail.setIdKhachHang(voucherDetailRequest.getKhachHang());
        return phieuGiamGiaChiTietRepository.save(voucherDetail);
    }

    @Override
    public ArrayList<PhieuGiamGiaChiTiet> getAllVoucherDetail(Long idPhieuGiamGia){
        return (ArrayList<PhieuGiamGiaChiTiet>) phieuGiamGiaChiTietRepository.getChiTietByPhieuGiamGia(idPhieuGiamGia);
    }

    @Override
    public ArrayList<PhieuGiamGiaChiTiet> getAllVoucherDetailByIdCustomer(Long idKhachHang){
        return phieuGiamGiaChiTietRepository.getChiTietByPhieuGiamGiaByIdKhachHang(idKhachHang);
    }

    @Override
    public void deleteVoucherDetail(Long id){
        phieuGiamGiaChiTietRepository.deleteById(id);
    }
}
