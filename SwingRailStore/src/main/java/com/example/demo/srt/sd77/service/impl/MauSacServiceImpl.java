package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.MauSac;
import com.example.demo.srt.sd77.repository.IMauSacRepository;
import com.example.demo.srt.sd77.service.IMauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MauSacServiceImpl implements IMauSacService {

    @Autowired
    private IMauSacRepository mauSacRepo;

    @Override
    public Page<MauSac> getColors(int pageNo, int pageSize, String key) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return mauSacRepo.findColorPanigation(pageable,
                "%" + key + "%");
    }

    @Override
    public ArrayList<MauSac> getAll() {
        return (ArrayList<MauSac>) mauSacRepo.findAll();
    }

    @Override
    public MauSac getById(Long id) {
        return mauSacRepo.findColorById(id).get(0);
    }

    @Override
    public MauSac add(String req) {
        MauSac color = new MauSac();

        if (!mauSacRepo.findColorByName(req).isEmpty()) {
            throw new RuntimeException("The color is already exists");
        }
        color.setTen(req);

        return mauSacRepo.save(color);
    }

    @Override
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
