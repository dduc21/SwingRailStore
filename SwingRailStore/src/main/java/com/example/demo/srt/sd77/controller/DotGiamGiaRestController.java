package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.entity.DotGiamGia;
import com.example.demo.srt.sd77.entity.request.SaleAddRequest;
import com.example.demo.srt.sd77.service.impl.DotGiamGiaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dot-giam-gia")
@CrossOrigin(origins = "*")
public class DotGiamGiaRestController {

    @Autowired
    private DotGiamGiaServiceImpl saleService;

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getVouchers(@RequestParam("page") Integer pageNo,
                                         @RequestParam("size") Integer pageSize,
                                         @RequestParam("key") String key,
                                         @RequestParam("trang_thai") String trangThai) {
        try {
            return new ResponseEntity<>(saleService.getSales(pageNo, pageSize, key, trangThai), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getVouchers() {
        try {
            return new ResponseEntity<>(saleService.getAllSales(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-sale")
    public ResponseEntity<?> addVoucher(@RequestBody SaleAddRequest req) {
        try {
            return new ResponseEntity<>(saleService.addSale(req), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(saleService.changeState(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-sale/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(saleService.getSaleById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit-sale")
    public ResponseEntity<?> editVoucher(@RequestBody DotGiamGia req) {
        try {
            return new ResponseEntity<>(saleService.editVoucher(req), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
