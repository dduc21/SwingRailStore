package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.repository.IGioHangChiTietRepository;
import com.example.demo.srt.sd77.service.IGioHangChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GioHangChiTietServiceImpl implements IGioHangChiTietService {

    @Autowired
    private IGioHangChiTietRepository gioHangChiTietRepo;

    @Override
    public void removeCartDetailById(Long id){
        gioHangChiTietRepo.deleteById(id);
    }

}
