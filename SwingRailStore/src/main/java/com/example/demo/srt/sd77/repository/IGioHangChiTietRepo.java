package com.example.demo.srt.sd77.repository;

import com.example.demo.srt.sd77.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IGioHangChiTietRepo extends JpaRepository<GioHangChiTiet, Long> {

    @Query(value = """
            select sum(so_luong) from gio_hang_chi_tiet ghct
            where id_gio_hang = :id
             """, nativeQuery = true)
    Long getQuantityOfCartDetailByIdCart(@Param("id") Long id);

    @Query(value = """
                select * from gio_hang_chi_tiet where id_gio_hang = :id 
            """, nativeQuery = true)
    ArrayList<GioHangChiTiet> findCartDetailsByIdCart(@Param("id") Long id);

    @Query(value = """
                select * from gio_hang_chi_tiet where id_gio_hang = :id and id_san_pham_chi_tiet = :idSanPham
            """, nativeQuery = true)
    ArrayList<GioHangChiTiet> findCartDetailsByIdCartAndProductDetail(@Param("id") Long id, @Param("idSanPham") Long idSanPham);

    @Query(value = """
                    select SUM(so_luong) from gio_hang_chi_tiet ghct
                    where ghct.id_gio_hang = :id
            """, nativeQuery = true)
    Long getQuantityOfCartDetailByIdCart2(@Param("id") Long id);
}
