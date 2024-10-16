package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.SanPhamChiTiet;
import com.example.demo.srt.sd77.entity.request.ProductDetailAddRequest;
import com.example.demo.srt.sd77.entity.request.ProductDetailSearchRequest;
import com.example.demo.srt.sd77.entity.responce.ProductDetailIdentityReponse;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public interface ISanPhamChiTietService {
    Page<SanPhamChiTiet> getProducts(int pageNo, int pageSize,
                                     Long id,
                                     String idKichCo, String idMauSac);

    ArrayList<SanPhamChiTiet> getAll();

    SanPhamChiTiet add(ProductDetailAddRequest req);

    SanPhamChiTiet update(SanPhamChiTiet req);

    Page<SanPhamChiTiet> findAllAndPanigation(int pageNo, int pageSize);

    Page<SanPhamChiTiet> getByIdString(ProductDetailSearchRequest req);

    SanPhamChiTiet addSale(SanPhamChiTiet req);

    ArrayList<SanPhamChiTiet> getProductDetailByIDSale(Long id);

    ArrayList<SanPhamChiTiet> getProductDetailNewest();

    ArrayList<SanPhamChiTiet> getTopProductBestSeller();

    ArrayList<SanPhamChiTiet> getProductDetailWithSales();

    ArrayList<ProductDetailIdentityReponse> getProductDetailIdentity(Long id);
}
