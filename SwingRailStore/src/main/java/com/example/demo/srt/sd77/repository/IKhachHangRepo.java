package com.example.demo.srt.sd77.repository;

import com.example.demo.srt.sd77.entity.KhachHang;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IKhachHangRepo extends JpaRepository<KhachHang, Long> {

    @Query(value = """
                select * from khach_hang v
                WHERE trang_thai like :trangThai 
                AND (ma like :key
                OR ten like :key)
                ORDER BY ngay_tao DESC
            """, nativeQuery = true)
    Page<KhachHang> findCustomers(Pageable pageable, @Param("trangThai") String trangThai, @Param("key") String key);

    @Query(value = """
                select * from khach_hang v
                where v.id = :id 
            """, nativeQuery = true)
    ArrayList<KhachHang> findKhachHangById(@Param("id") Long id);

    @Query(value = """
                select top 1 ma from khach_hang order by ngay_tao desc
            """, nativeQuery = true)
    String generateNewestCode();

    @Query(value = """
                select * from khach_hang
                where email = :email and mat_khau = :matKhau
            """, nativeQuery = true)
    KhachHang findKhachHangByEmailAndPass(@Param("email") String email, @Param("matKhau") String pass);

    @Query(value = """
                select * from khach_hang
                where email = :email 
            """, nativeQuery = true)
    KhachHang findKhachHangByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = """
              update khach_hang set mat_khau = :matKhau where id = :id
            """, nativeQuery = true)
    void changePass(@Param("matKhau") String matKhau, @Param("id") String id);

}
