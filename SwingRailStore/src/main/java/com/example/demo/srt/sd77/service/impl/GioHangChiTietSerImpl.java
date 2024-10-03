package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.GioHang;
import com.example.demo.srt.sd77.entity.GioHangChiTiet;
import com.example.demo.srt.sd77.repository.IGioHangChiTietRepo;
import com.example.demo.srt.sd77.repository.IGioHangRepo;
import com.example.demo.srt.sd77.service.IGioHangChiTietSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GioHangChiTietSerImpl implements IGioHangChiTietSer {

    @Autowired
    private IGioHangChiTietRepo gioHangChiTietRepo;

    public void removeCartDetailById(Long id){
        gioHangChiTietRepo.deleteById(id);
    }

}
