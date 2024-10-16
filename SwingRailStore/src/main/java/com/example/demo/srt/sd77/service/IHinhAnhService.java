package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.HinhAnh;
import com.example.demo.srt.sd77.entity.TheLoai;
import com.example.demo.srt.sd77.entity.request.ImageAddRequest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public interface IHinhAnhService {
    Page<HinhAnh> getImages(int pageNo, int pageSize, String key, Long id);

    ArrayList<HinhAnh> getAll(Long id);

    HinhAnh getById(Long id);

    HinhAnh add(ImageAddRequest req);

    public TheLoai update(TheLoai req);

    public void deleteImageById(Long id);
}
