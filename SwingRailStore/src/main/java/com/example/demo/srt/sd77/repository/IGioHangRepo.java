package com.example.demo.srt.sd77.repository;

import com.example.demo.srt.sd77.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IGioHangRepo extends JpaRepository<GioHang, Long> {

    @Query(value = """
                    select * from gio_hang where id_khach_hang is null
            """, nativeQuery = true)
    ArrayList<GioHang> getGioHangKhongDangNhap();

    @Query(value = """
                    select * from gio_hang where id_khach_hang = :id
            """, nativeQuery = true)
    ArrayList<GioHang> getCartByIdKhachHang(@Param("id") Long id);

}
