package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.service.impl.GioHangChiTietSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart-detail")
@CrossOrigin("*")
public class CartDetailRestController {

    @Autowired
    private GioHangChiTietSerImpl gioHangChiTietService;

    @DeleteMapping("/remove-cart-detail/{id}")
    public ResponseEntity<?> removeCartDetail(@PathVariable("id") Long id) {
        try {
            gioHangChiTietService.removeCartDetailById(id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
