package com.example.demo.srt.sd77.entity.responce;

import com.example.demo.srt.sd77.entity.HoaDon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {HoaDon.class})
public interface RevenueRangeDateResponce {

    @Value("#{target.so_luong}")
    Integer getSoLuong();

    @Value("#{target.tong_tien}")
    BigDecimal getTongTien();

    @Value("#{target.ngay}")
    Date getNgay();

}
