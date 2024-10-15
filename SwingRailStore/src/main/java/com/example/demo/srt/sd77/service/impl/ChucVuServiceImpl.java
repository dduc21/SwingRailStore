package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.ChucVu;
import com.example.demo.srt.sd77.repository.IChucVuRepository;
import com.example.demo.srt.sd77.service.IChucVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChucVuServiceImpl implements IChucVuService {

    @Autowired
    private IChucVuRepository chucVuRepo;

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
