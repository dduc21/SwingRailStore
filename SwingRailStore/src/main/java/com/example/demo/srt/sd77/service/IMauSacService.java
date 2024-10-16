package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.LichSu;
import com.example.demo.srt.sd77.entity.MauSac;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public interface IMauSacService {
    Page<MauSac> getColors(int pageNo, int pageSize, String key);

    ArrayList<MauSac> getAll();

    MauSac getById(Long id);

    MauSac add(String req);

    MauSac update(MauSac req);
}
