package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.HinhAnh;
import com.example.demo.srt.sd77.entity.SanPham;
import com.example.demo.srt.sd77.entity.SanPhamChiTiet;
import com.example.demo.srt.sd77.entity.TheLoai;
import com.example.demo.srt.sd77.entity.request.ImageAddRequest;
import com.example.demo.srt.sd77.entity.request.ProductAddRequest;
import com.example.demo.srt.sd77.repository.IHinhAnhRepo;
import com.example.demo.srt.sd77.repository.ISanPhamChiTietRepo;
import com.example.demo.srt.sd77.service.IHinhAnhSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HinhAnhSerImpl implements IHinhAnhSer {

    @Autowired
    private IHinhAnhRepo hinhAnhRepo;

    @Autowired
    private ISanPhamChiTietRepo sanPhamChiTietRepo;

    public Page<HinhAnh> getImages(int pageNo, int pageSize, String key, Long id){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return hinhAnhRepo.findPanigation(pageable, id);
    }

    public ArrayList<HinhAnh> getAll(Long id){
//        get all voucher
        return (ArrayList<HinhAnh>) hinhAnhRepo.findByOrderByNgayTaoDesc(id);
    }

    public HinhAnh getById(Long id){
        return hinhAnhRepo.findImageById(id).get(0);
    }

    public HinhAnh add(ImageAddRequest req){
        HinhAnh e = new HinhAnh();

        ArrayList<SanPhamChiTiet> sanPhamChiTiets = sanPhamChiTietRepo.findProductDetailById(req.getIdSanPhamChiTiet());

        if(sanPhamChiTiets.size() == 0){
            throw new RuntimeException("Không tìm thấy sản pham này");
        }
        e.setTrangThai(true);
        e.setIdSanPhamChiTiet(sanPhamChiTiets.get(0));
        e.setDuongDan(req.getDuongDan());

        return hinhAnhRepo.save(e);
    }

    public TheLoai update(TheLoai req){
        return null;
    }

    public void deleteImageById(Long id){
        hinhAnhRepo.deleteImageById(id);
    }

}
