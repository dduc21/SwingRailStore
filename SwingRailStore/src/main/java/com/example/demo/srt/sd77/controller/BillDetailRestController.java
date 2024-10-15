package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.entity.HoaDonChiTiet;
import com.example.demo.srt.sd77.entity.request.ProductDetailRequest;
import com.example.demo.srt.sd77.service.impl.HoaDonChiTietServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/bill-detail")
@CrossOrigin("*")
public class BillDetailRestController {

    @Autowired
    private HoaDonChiTietServiceImpl hoaDonChiTietService;

    @GetMapping("/get-by-bill")
    public ResponseEntity<?> getByProduct(@RequestParam("id") Long id,
                                          @RequestParam("page") Integer pageNo,
                                          @RequestParam("size") Integer pageSize) {
        try {
            return new ResponseEntity<>(hoaDonChiTietService.findProductDetailsByIdProduct(pageNo, pageSize, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-product-to-bill")
    public ResponseEntity<?> addToBill(@RequestBody ProductDetailRequest req) {
        try {
            return new ResponseEntity<>(hoaDonChiTietService.addProductToBill(req), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-product-to-bill-refund")
    public ResponseEntity<?> addToBillRefund(@RequestBody ProductDetailRequest req) {
        try {
            return new ResponseEntity<>(hoaDonChiTietService.addProductToBillRefund(req), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-bill-detail-client")
    public ResponseEntity<?> addToBillClient(@RequestBody ProductDetailRequest req) {
        try {
            return new ResponseEntity<>(hoaDonChiTietService.addProductToBillClient(req), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove-by-id/{id}")
    public void deleteProductDetailByID(@PathVariable("id") Long id) {
        try {
            hoaDonChiTietService.removeBillDetailById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/refund-single")
    public void deleteProductDetailByID(@RequestBody HoaDonChiTiet req) {
        try {
            hoaDonChiTietService.updateBillDetail(req);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get-bill-detail-state")
    public ArrayList<HoaDonChiTiet> getBillDetailState(@RequestParam("state") Integer state, @RequestParam("id") Long id) {
        try {
            return hoaDonChiTietService.getBillDetailByState(state, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/check-bill-detail-client")
    public ResponseEntity<?> checkBillDetailClient(@RequestBody ProductDetailRequest req) {
        try {
            return new ResponseEntity<>(hoaDonChiTietService.checkBillDetail(req), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.BAD_REQUEST);
        }
    }

}
