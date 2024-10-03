package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.TheLoai;
import com.example.demo.srt.sd77.repository.ITheLoaiRepo;
import com.example.demo.srt.sd77.service.ITheLoaiSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheLoaiSerImpl implements ITheLoaiSer {

    @Autowired
    private ITheLoaiRepo theLoaiRepo;

    public Page<TheLoai> getTypes(int pageNo, int pageSize, String key) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return theLoaiRepo.findPanigation(pageable,
                "%" + key + "%");
    }

    public ArrayList<TheLoai> getAll() {
//        get all voucher
        return (ArrayList<TheLoai>) theLoaiRepo.findByOrderByNgayTaoDesc();
    }

    public TheLoai getById(Long id) {
        return theLoaiRepo.findTypeById(id).get(0);
    }

    public TheLoai add(String req) {
        TheLoai e = new TheLoai();

        if (!theLoaiRepo.findTypeByName(req).isEmpty()) {
            throw new RuntimeException("The type is already exists");
        }
        e.setTen(req);

        return theLoaiRepo.save(e);
    }

    public TheLoai update(TheLoai req) {
        List<TheLoai> brands = theLoaiRepo.findTypeByName(req.getTen());

        if (!brands.isEmpty()) {
            if (!brands.get(0).getId().equals(req.getId())) {
                if (brands.get(0).getTen().equals(req.getTen())) {
                    throw new RuntimeException("The color is already exists");
                }
            }
        }

        return theLoaiRepo.save(req);
    }
}
