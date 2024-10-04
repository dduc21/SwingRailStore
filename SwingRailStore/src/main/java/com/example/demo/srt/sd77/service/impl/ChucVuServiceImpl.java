package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.ChucVu;
import com.example.demo.srt.sd77.repository.IChucVuRepo;
import com.example.demo.srt.sd77.service.IChucVu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChucVuServiceImpl implements IChucVu {

    @Autowired
    private IChucVuRepo chucVuRepo;

    @Override
    public ChucVu findById(int id) {
        for (var temp : chucVuRepo.findAll()) {
            if (temp.getId() == id) {
                return temp;
            }
        }
        return null;
    }
}
