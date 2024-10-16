package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.HinhAnh;
import com.example.demo.srt.sd77.entity.SanPhamChiTiet;
import com.example.demo.srt.sd77.entity.TheLoai;
import com.example.demo.srt.sd77.entity.request.ImageAddRequest;
import com.example.demo.srt.sd77.repository.IHinhAnhRepository;
import com.example.demo.srt.sd77.repository.ISanPhamChiTietRepository;
import com.example.demo.srt.sd77.service.IHinhAnhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HinhAnhServiceImpl implements IHinhAnhService {

    @Autowired
    private IHinhAnhRepository hinhAnhRepo;

    @Autowired
    private ISanPhamChiTietRepository sanPhamChiTietRepo;

    @Override
    public Page<HinhAnh> getImages(int pageNo, int pageSize, String key, Long id) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return hinhAnhRepo.findPanigation(pageable, id);
    }

    @Override
    public ArrayList<HinhAnh> getAll(Long id) {
        return (ArrayList<HinhAnh>) hinhAnhRepo.findByOrderByNgayTaoDesc(id);
    }

    @Override
    public HinhAnh getById(Long id) {
        return hinhAnhRepo.findImageById(id).get(0);
    }

    @Override
    public HinhAnh add(ImageAddRequest req) {
        HinhAnh e = new HinhAnh();

        ArrayList<SanPhamChiTiet> sanPhamChiTiets = sanPhamChiTietRepo.findProductDetailById(req.getIdSanPhamChiTiet());

        if (sanPhamChiTiets.size() == 0) {
            throw new RuntimeException("Không tìm thấy sản pham này");
        }
        e.setTrangThai(true);
        e.setIdSanPhamChiTiet(sanPhamChiTiets.get(0));
        e.setDuongDan(req.getDuongDan());

        return hinhAnhRepo.save(e);
    }

    @Override
    public TheLoai update(TheLoai req) {
        return null;
    }

    @Override
    public void deleteImageById(Long id) {
        hinhAnhRepo.deleteImageById(id);
    }

}
