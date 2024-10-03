package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.entity.ThuongHieu;
import com.example.demo.srt.sd77.service.impl.ThuongHieuSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
@CrossOrigin("*")
public class BrandRestController {

    @Autowired
    private ThuongHieuSerImpl thuongHieuService;

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getVouchers(@RequestParam("page")Integer pageNo,
                                         @RequestParam("size")Integer pageSize,
                                         @RequestParam("key")String key,
                                         @RequestParam("trang_thai")String trangThai){
        try{
            return new ResponseEntity<>(thuongHieuService.getBrands(pageNo, pageSize, key), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getVouchers(){
        try{
            return new ResponseEntity<>(thuongHieuService.getAllBrands(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestParam("name") String req){
        try{
            return new ResponseEntity<>(thuongHieuService.addBrand(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-brand/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(thuongHieuService.getBrandById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> editVoucher(@RequestBody ThuongHieu req){
        try{
            return new ResponseEntity<>(thuongHieuService.updateBrand(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
