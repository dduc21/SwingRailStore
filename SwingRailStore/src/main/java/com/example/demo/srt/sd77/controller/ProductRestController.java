package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.entity.SanPhamChiTiet;
import com.example.demo.srt.sd77.entity.request.ProductAddRequest;
import com.example.demo.srt.sd77.entity.request.ProductUpdateRequest;
import com.example.demo.srt.sd77.service.impl.SanPhamSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductRestController {

    @Autowired
    private SanPhamSerImpl sanPhamService;

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getVouchers(@RequestParam("page")Integer pageNo,
                                         @RequestParam("size")Integer pageSize,
                                         @RequestParam("key")String key,
                                         @RequestParam("id_type")String idType,
                                         @RequestParam("id_brand")String idBrand,
                                         @RequestParam("trang_thai")String trangThai){
        try{
            return new ResponseEntity<>(sanPhamService.getProducts(pageNo, pageSize, key, idBrand, idType), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getVouchers(){
        try{
            return new ResponseEntity<>(sanPhamService.getAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestBody ProductAddRequest req){
        try{
            return new ResponseEntity<>(sanPhamService.add(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-product/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(sanPhamService.getById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-max-don-gia")
    public ResponseEntity<?> getMaxDonGia(){
        try{
            return new ResponseEntity<>(sanPhamService.getMaxDonGia(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdateRequest req){
        try{
            return new ResponseEntity<>(sanPhamService.updateProduct(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-quantity-of-product-by-brand")
    public ResponseEntity<?> getQuantitysOfProductByBrand(){
        try{
            return new ResponseEntity<>(sanPhamService.getQuantitysByBrand(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-quantity-of-product-by-type")
    public ResponseEntity<?> getQuantitysOfProductByType(){
        try{
            return new ResponseEntity<>(sanPhamService.getQuantitysByType(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-quantity-of-product-by-size")
    public ResponseEntity<?> getQuantitysOfProductBySize(){
        try{
            return new ResponseEntity<>(sanPhamService.getQuantitysBySize(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-quantity-of-product-by-color")
    public ResponseEntity<?> getQuantitysOfProductByColor(){
        try{
            return new ResponseEntity<>(sanPhamService.getQuantitysByColor(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
