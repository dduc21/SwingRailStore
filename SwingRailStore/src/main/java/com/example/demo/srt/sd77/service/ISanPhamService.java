package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.SanPham;
import com.example.demo.srt.sd77.entity.request.ProductAddRequest;
import com.example.demo.srt.sd77.entity.request.ProductUpdateRequest;
import com.example.demo.srt.sd77.entity.responce.ProductResponce;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface ISanPhamService {
    Page<ProductResponce> getProducts(int pageNo,
                                      int pageSize,
                                      String key,
                                      String idBrand,
                                      String idType);

    ArrayList<SanPham> getAll();

    SanPham getById(Long id);

    SanPham add(ProductAddRequest req);

    SanPham update(SanPham req);

    String generateCode();

    BigDecimal getMaxDonGia();

    SanPham updateProduct(ProductUpdateRequest req);

    ArrayList<Object> getQuantitysByType();

    ArrayList<Object> getQuantitysByBrand();

    ArrayList<Object> getQuantitysBySize();

    ArrayList<Object> getQuantitysByColor();
}
