package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.entity.request.PaymentMethodAddRequest;
import com.example.demo.srt.sd77.service.impl.HinhThucThanhToanSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment-method")
@CrossOrigin("*")
public class PaymentMethodRestController {

    @Autowired
    private HinhThucThanhToanSerImpl paymentMethodSer;

    @PostMapping("/add")
    public ResponseEntity<?> addPaymentMethod(@RequestBody PaymentMethodAddRequest req) {
        try {
            return new ResponseEntity<>(paymentMethodSer.addPaymentMethod(req), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all/{id}")
    public ResponseEntity<?> getALlPaymentMethod(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(paymentMethodSer.getAllPaymentMethodByIdBill(id), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }
}
