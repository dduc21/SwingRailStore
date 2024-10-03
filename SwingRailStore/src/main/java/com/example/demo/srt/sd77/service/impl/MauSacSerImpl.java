package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.MauSac;
import com.example.demo.srt.sd77.repository.IMauSacRepo;
import com.example.demo.srt.sd77.service.IMauSacSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MauSacSerImpl implements IMauSacSer {

    @Autowired
    private IMauSacRepo mauSacRepo;

    public Page<MauSac> getColors(int pageNo, int pageSize, String key) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return mauSacRepo.findColorPanigation(pageable,
                "%" + key + "%");
    }

    public ArrayList<MauSac> getAll() {
//        get all voucher
        return (ArrayList<MauSac>) mauSacRepo.findAll();
    }

    public MauSac getById(Long id) {
        return mauSacRepo.findColorById(id).get(0);
    }

    public MauSac add(String req) {
        MauSac color = new MauSac();

        if (!mauSacRepo.findColorByName(req).isEmpty()) {
            throw new RuntimeException("The color is already exists");
        }
        color.setTen(req);

        return mauSacRepo.save(color);
    }

    public MauSac update(MauSac req) {
        List<MauSac> brands = mauSacRepo.findColorByName(req.getTen());

        if (!brands.isEmpty()) {
            if (!brands.get(0).getId().equals(req.getId())) {
                if (brands.get(0).getTen().equals(req.getTen())) {
                    throw new RuntimeException("The color is already exists");
                }
            }
        }

        return mauSacRepo.save(req);
    }
}
