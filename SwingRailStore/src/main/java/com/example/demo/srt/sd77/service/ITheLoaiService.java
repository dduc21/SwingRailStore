package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.TheLoai;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public interface ITheLoaiService {
    Page<TheLoai> getTypes(int pageNo, int pageSize, String key);

    ArrayList<TheLoai> getAll();

    TheLoai getById(Long id);

    TheLoai add(String req);

    TheLoai update(TheLoai req);
}
