package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.entity.request.ImageAddRequest;
import com.example.demo.srt.sd77.service.impl.HinhAnhSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image")
@CrossOrigin("*")
public class ImageRestController {

    @Autowired
    private HinhAnhSerImpl hinhAnhService;

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getImages(@RequestParam("page")Integer pageNo,
                                         @RequestParam("size")Integer pageSize,
                                         @RequestParam("key")String key,
                                         @RequestParam("trang_thai")String trangThai,
                                         @RequestParam("id")Long id){
        try{
            return new ResponseEntity<>(hinhAnhService.getImages(pageNo, pageSize, key, id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all/{id}")
    public ResponseEntity<?> getImages(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(hinhAnhService.getAll(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestBody ImageAddRequest req){
        try{
            return new ResponseEntity<>(hinhAnhService.add(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable("id")Long id){
        try{
            hinhAnhService.deleteImageById(id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
