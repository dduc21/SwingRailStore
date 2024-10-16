package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.DotGiamGia;
import com.example.demo.srt.sd77.entity.request.SaleAddRequest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public interface IDotGiamGiaService {
    Page<DotGiamGia> getSales(int pageNo, int pageSize, String key, String trangThai);

    ArrayList<DotGiamGia> getAllSales();

    DotGiamGia getSaleById(Long id);

    DotGiamGia addSale(SaleAddRequest req);

    String changeState(Long id);

    String generateCode();

    DotGiamGia editVoucher(DotGiamGia sale);
}
