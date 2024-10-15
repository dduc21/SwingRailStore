package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.HinhThucThanhToan;
import com.example.demo.srt.sd77.entity.request.PaymentMethodAddRequest;
import com.example.demo.srt.sd77.repository.IHinhThucThanhToanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HinhThucThanhToanServiceImpl {

    @Autowired
    private IHinhThucThanhToanRepository hinhThucThanhToanRepo;

    public HinhThucThanhToan addPaymentMethod(PaymentMethodAddRequest req){
        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();

        hinhThucThanhToan.setLoaiThanhToan(req.getLoaiThanhToan());
        hinhThucThanhToan.setSoTienThanhToan(req.getSoTienThanhToan());
        hinhThucThanhToan.setGhiChu(req.getGhiChu());
        hinhThucThanhToan.setIdHoaDon(req.getIdHoaDon());
        hinhThucThanhToan.setTrangThai(req.getDeleted());
        return hinhThucThanhToanRepo.save(hinhThucThanhToan);
    }

    public ArrayList<HinhThucThanhToan> getAllPaymentMethodByIdBill(Long id){
        return hinhThucThanhToanRepo.getAllPaymentMethodByIdBill(id);
    }

}
