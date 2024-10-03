package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.SanPham;
import com.example.demo.srt.sd77.entity.SanPhamChiTiet;
import com.example.demo.srt.sd77.entity.request.ProductAddRequest;
import com.example.demo.srt.sd77.entity.request.ProductUpdateRequest;
import com.example.demo.srt.sd77.entity.responce.ProductResponce;
import com.example.demo.srt.sd77.repository.IKichCoRepo;
import com.example.demo.srt.sd77.repository.IMauSacRepo;
import com.example.demo.srt.sd77.repository.ISanPhamChiTietRepo;
import com.example.demo.srt.sd77.repository.ISanPhamRepo;
import com.example.demo.srt.sd77.repository.ITheLoaiRepo;
import com.example.demo.srt.sd77.repository.IThuongHieuRepo;
import com.example.demo.srt.sd77.service.ISanPhamSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class SanPhamSerImpl implements ISanPhamSer {

    @Autowired
    private ISanPhamRepo sanPhamRepo;

    @Autowired
    private ITheLoaiRepo theLoaiRepo;

    @Autowired
    private IThuongHieuRepo thuongHieuRepo;

    @Autowired
    private IKichCoRepo kichCoRepo;

    @Autowired
    private IMauSacRepo mauSacRepo;

    @Autowired
    private ISanPhamChiTietRepo sanPhamChiTietRepo;

    public Page<ProductResponce> getProducts(int pageNo,
                                             int pageSize,
                                             String key,
                                             String idBrand,
                                             String idType) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return sanPhamRepo.findPanigation(pageable,
                "%" + key + "%",
                "%" + idType + "%",
                "%" + idBrand + "%");
    }

    public ArrayList<SanPham> getAll() {
//        get all voucher
        return (ArrayList<SanPham>) sanPhamRepo.findByOrderByNgayTaoDesc();
    }

    public SanPham getById(Long id) {
        return sanPhamRepo.findProductById(id).get(0);
    }

    public SanPham add(ProductAddRequest req) {
        SanPham e = new SanPham();

        if (!sanPhamRepo.findProductByName(req.getTen()).isEmpty()) {
            throw new RuntimeException("The type is already exists");
        }
        e.setTen(req.getTen());
        e.setTrangThai(true);
        e.setMoTa(req.getMoTa());
        e.setIdTheLoai(req.getIdTheLoai());
        e.setIdThuongHieu(req.getIdThuongHieu());
        e.setMa(generateCode());

        return sanPhamRepo.save(e);
    }

    public SanPham update(SanPham req) {
        return sanPhamRepo.save(req);
    }

    public String generateCode() {
        // generate code
        String newestCode = sanPhamRepo.generateNewestCode();
        if (newestCode == null || newestCode.equals("")) {
            return "PRODUCT_" + 0;
        }
        return "PRODUCT_" + (Integer.parseInt(newestCode.substring(8)) + 1);
    }

    public BigDecimal getMaxDonGia() {
        return sanPhamRepo.getMaxDonGia();
    }

    public SanPham updateProduct(ProductUpdateRequest req) {
        SanPham e = sanPhamRepo.findProductById(req.getId()).get(0);

        if (e == null) {
            throw new RuntimeException("Product not found");
        }

        ArrayList<SanPham> products = sanPhamRepo.findProductByName(req.getTen());
        if (!products.isEmpty()) {
            if (!Objects.equals(products.get(0).getId(), req.getId())) {
                throw new RuntimeException("The product is already exists");
            }
        }

        e.setTen(req.getTen());
        e.setIdTheLoai(req.getIdTheLoai());
        e.setIdThuongHieu(req.getIdThuongHieu());
        e.setTrangThai(req.getTrangThai());

        if (req.getTrangThai() == false) {
            // trạng thái khi san pham da bao la khong con kinh doanh
            // tat ca cac san pham chi tiet se bao la het hang va khong cho phep kinh doanh nua
            ArrayList<SanPhamChiTiet> productDetails = sanPhamChiTietRepo.getProductDetailsByIdProduct(req.getId());
            if (!productDetails.isEmpty()) {
                for (SanPhamChiTiet productDetail : productDetails) {
                    productDetail.setTrangThai(false);
                    sanPhamChiTietRepo.save(productDetail);
                }
            }

        } else {
            ArrayList<SanPhamChiTiet> productDetails = sanPhamChiTietRepo.getProductDetailsByIdProduct(req.getId());
            if (!productDetails.isEmpty()) {
                for (SanPhamChiTiet productDetail : productDetails) {
                    productDetail.setTrangThai(true);
                    sanPhamChiTietRepo.save(productDetail);
                }
            }
        }
        return sanPhamRepo.save(e);
    }

    public ArrayList<Object> getQuantitysByType() {
        return theLoaiRepo.getQuantitysOfProductByType();
    }

    public ArrayList<Object> getQuantitysByBrand() {
        return thuongHieuRepo.getQuantitysOfProductByBrand();
    }

    public ArrayList<Object> getQuantitysBySize() {
        return kichCoRepo.getQuantitysOfProductBySize();
    }

    public ArrayList<Object> getQuantitysByColor() {
        return mauSacRepo.getQuantitysOfProductByColor();
    }

}
