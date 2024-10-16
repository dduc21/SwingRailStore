package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.GioHang;
import com.example.demo.srt.sd77.entity.GioHangChiTiet;
import com.example.demo.srt.sd77.entity.request.CartDetailRequest;

import java.util.ArrayList;

public interface IGioHangService {
    GioHang getCartByIdCustomer(Long id);

    Long getQuantityOfCartDetailByIdCustomer(Long id);

    GioHangChiTiet addProductToCart(CartDetailRequest req);

    GioHangChiTiet addProductToCartPlusQuantity(CartDetailRequest req);

    ArrayList<GioHangChiTiet> findCartDetailsByIdCart(Long id);

    ArrayList<GioHangChiTiet> findCartDetailsByIdCartAndProductDetail(Long id, Long idSanPham);
}
