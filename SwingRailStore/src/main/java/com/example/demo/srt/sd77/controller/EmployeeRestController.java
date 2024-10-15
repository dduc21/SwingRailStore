package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.entity.request.EmployeeAddRequest;
import com.example.demo.srt.sd77.service.impl.NhanVienServiceImpl;
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
@RequestMapping("/employee")
@CrossOrigin("*")
public class EmployeeRestController {

    @Autowired
    private NhanVienServiceImpl employeeSer;

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getVouchers(@RequestParam("page") Integer pageNo,
                                         @RequestParam("size") Integer pageSize,
                                         @RequestParam("key") String key,
                                         @RequestParam("trang_thai") String trangThai) {
        try {
            return new ResponseEntity<>(employeeSer.getEmployeesWithPanigation(pageNo, pageSize, key, trangThai), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeAddRequest req) {
        try {
            System.out.println(req.getChucVu().toString());
            return new ResponseEntity<>(employeeSer.addEmployee(req), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-employee/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(employeeSer.getEmployeeByID(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeAddRequest req) {
        try {
            return new ResponseEntity<>(employeeSer.updateEmployee(req), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("user") String username, @RequestParam("pass") String password) {
        try {
            return new ResponseEntity<>(employeeSer.login(username, password), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/change-pass")
    public ResponseEntity<?> changePass(@RequestParam("oldPass") String pass,
                                        @RequestParam("newPass") String newPass,
                                        @RequestParam("id") Long id) {
        try {
            return new ResponseEntity<>(employeeSer.changePass(pass, newPass, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
