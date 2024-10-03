package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.entity.Voucher;
import com.example.demo.srt.sd77.entity.request.VoucherAddRequest;
import com.example.demo.srt.sd77.service.impl.VoucherSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voucher")
@CrossOrigin(origins = "*")
public class VoucherController {

    @Autowired
    private VoucherSerImpl voucherService;

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getVouchers(@RequestParam("page")Integer pageNo,
                                         @RequestParam("size")Integer pageSize,
                                         @RequestParam("key")String key,
                                         @RequestParam("trang_thai")String trangThai){
        try{
            return new ResponseEntity<>(voucherService.getVouchers(pageNo, pageSize, key, trangThai), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getVouchers(@RequestParam("key")String key){
        try{
            return new ResponseEntity<>(voucherService.getAllVoucher(key), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-voucher")
    public ResponseEntity<?> addVoucher(@RequestBody VoucherAddRequest req){
        try{
            return new ResponseEntity<>(voucherService.addVoucher(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(voucherService.changeState(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-voucher/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(voucherService.getVoucherById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit-voucher")
    public ResponseEntity<?> editVoucher(@RequestBody Voucher req){
        try{
            return new ResponseEntity<>(voucherService.editVoucher(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-voucher-absolute")
    public ResponseEntity<?> getVoucherAbsolute(@RequestParam("id_voucher")String key){
        try{
            return new ResponseEntity<>(voucherService.getVouchersByKeyAbsolute(key), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
