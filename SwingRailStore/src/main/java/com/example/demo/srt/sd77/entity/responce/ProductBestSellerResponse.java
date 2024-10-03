package com.example.demo.srt.sd77.entity.responce;

import com.example.demo.srt.sd77.entity.SanPhamChiTiet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = SanPhamChiTiet.class)
public interface ProductBestSellerResponse {

    @Value("#{target.id}")
    Long getId();

    @Value("#{target.so_luong}")
    Long getSoLuong();

    @Value("#{target.tong_tien}")
    BigDecimal getTongTien();
}
