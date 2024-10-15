package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.entity.request.VoucherDetailRequest;
import com.example.demo.srt.sd77.service.impl.PhieuGiamGiaChiTietServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voucher-detail")
@CrossOrigin("*")
public class VoucherDetailRestController {

    @Autowired
    private PhieuGiamGiaChiTietServiceImpl phieuGiamGiaChiTietSer;

    @PostMapping("/add-voucher-detail")
    public ResponseEntity<?> addVoucherDetail(@RequestBody VoucherDetailRequest voucherDetailRequest) {
        try {
            return new ResponseEntity<>(phieuGiamGiaChiTietSer.addVoucherDetail(voucherDetailRequest), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-by-id-voucher/{id}")
    public ResponseEntity<?> getAllVoucherDetail(@PathVariable("id") Long idPhieuGiamGia) {
        try {
            return new ResponseEntity<>(phieuGiamGiaChiTietSer.getAllVoucherDetail(idPhieuGiamGia), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-by-id-customer/{id}")
    public ResponseEntity<?> getAllVoucherDetailByIDCustomer(@PathVariable("id") Long idPhieuGiamGia) {
        try {
            return new ResponseEntity<>(phieuGiamGiaChiTietSer.getAllVoucherDetailByIdCustomer(idPhieuGiamGia), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<?> deleteVoucherDetail(@PathVariable("id") Long id) {
        try {
            phieuGiamGiaChiTietSer.deleteVoucherDetail(id);
            return new ResponseEntity<>("Xóa thành cong", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }
}
