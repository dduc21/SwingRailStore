package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.ThuongHieu;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public interface IThuongHieuService {
    Page<ThuongHieu> getBrands(int pageNo, int pageSize, String key);

    ArrayList<ThuongHieu> getAllBrands();

    ThuongHieu getBrandById(Long id);

    ThuongHieu addBrand(String req);

    ThuongHieu updateBrand(ThuongHieu req);
}
