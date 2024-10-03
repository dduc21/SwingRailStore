package com.example.demo.srt.sd77.entity.responce;

import com.example.demo.srt.sd77.entity.SanPhamChiTiet;
import com.example.demo.srt.sd77.entity.TheLoai;
import com.example.demo.srt.sd77.entity.ThuongHieu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {SanPhamChiTiet.class, ThuongHieu.class, TheLoai.class})
public interface ProductResponce {

    @Value("#{target.id}")
    Long getId();

    @Value("#{target.ten}")
    String getTen();

    @Value("#{target.mo_ta}")
    String getMoTa();

    @Value("#{target.ten_the_loai}")
    String getTenTheLoai();

    @Value("#{target.ten_thuong_hieu}")
    String getTenThuongHieu();

    @Value("#{target.so_luong}")
    Integer getSoLuong();

    @Value("#{target.trang_thai}")
    Boolean getTrangThai();
}
