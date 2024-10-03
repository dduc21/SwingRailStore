package com.example.demo.srt.sd77.entity.responce;

import com.example.demo.srt.sd77.entity.SanPhamChiTiet;
import com.example.demo.srt.sd77.entity.TheLoai;
import com.example.demo.srt.sd77.entity.ThuongHieu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {SanPhamChiTiet.class, ThuongHieu.class, TheLoai.class})
public interface ProductBestSellerResponce {

    @Value("#{target.ten}")
    String getTen();

    @Value("#{target.so_luong}")
    Long getSoLuong();

    @Value("#{target.tong_tien}")
    BigDecimal getTongTien();

}
