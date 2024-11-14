package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.entity.request.HistoryRequest;
import com.example.demo.srt.sd77.service.impl.LichSuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
@CrossOrigin("*")
public class HistoryRestController {

    @Autowired
    private LichSuServiceImpl lichSuService;
    @GetMapping("/get-all-by-id/{id}")
    public ResponseEntity<?> getAllHistory(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(lichSuService.getAllLichSu(id), HttpStatus.OK);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    //aaaaaaa

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody HistoryRequest history){
        try{
            return new ResponseEntity<>(lichSuService.addHistory(history), HttpStatus.OK);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
