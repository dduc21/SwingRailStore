package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.entity.KhachHang;
import com.example.demo.srt.sd77.entity.request.CustomerAddRequest;
import com.example.demo.srt.sd77.entity.request.CustomerRegisterRequest;
import com.example.demo.srt.sd77.service.impl.KhachHangSerImpl;
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
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class CustomerRestController {

    @Autowired
    private KhachHangSerImpl customerSer;

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getVouchers(@RequestParam("page") Integer pageNo,
                                         @RequestParam("size") Integer pageSize,
                                         @RequestParam("key") String key,
                                         @RequestParam("trang_thai") String trangThai) {
        try {
            return new ResponseEntity<>(customerSer.getCustomersWithPanigation(pageNo, pageSize, key, trangThai), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestBody CustomerAddRequest req) {
        try {
            return new ResponseEntity<>(customerSer.addCustomer(req), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-customer/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(customerSer.getCustomerById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody KhachHang khachHang) {
        try {
            return new ResponseEntity<>(customerSer.updateCustomer(khachHang), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegisterRequest req) {
        try {
            return new ResponseEntity<>(customerSer.register(req), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam("user") String username, @RequestParam("pass") String password) {
        try {
            return new ResponseEntity<>(customerSer.login(username, password), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change-pass")
    public ResponseEntity<?> changePass(@RequestParam("id") String id, @RequestParam("pass") String password) {
        try {
            customerSer.changePass(password, id);
            return new ResponseEntity<>("Thành công", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
