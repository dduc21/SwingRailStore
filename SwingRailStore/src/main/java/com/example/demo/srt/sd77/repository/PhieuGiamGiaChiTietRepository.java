package com.example.demo.srt.sd77.repository;

import com.example.demo.srt.sd77.entity.PhieuGiamGiaChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

public interface PhieuGiamGiaChiTietRepository extends JpaRepository<PhieuGiamGiaChiTiet, Long> {

    @Query(value = """
            select * from phieu_giam_gia_chi_tiet
             where id_phieu_giam_gia = :idPhieuGiamGia
    """, nativeQuery = true)
    ArrayList<PhieuGiamGiaChiTiet> getChiTietByPhieuGiamGia(@RequestParam("idPhieuGiamGia") Long idPhieuGiamGia);

    @Query(value = """
        select * from phieu_giam_gia_chi_tiet
         where id_khach_hang = :idKhachHang
    """, nativeQuery = true)
    ArrayList<PhieuGiamGiaChiTiet> getChiTietByPhieuGiamGiaByIdKhachHang(@RequestParam("idKhachHang") Long idKhachHang);
}
