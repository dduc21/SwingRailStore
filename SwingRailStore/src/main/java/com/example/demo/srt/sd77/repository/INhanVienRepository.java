package com.example.demo.srt.sd77.repository;

import com.example.demo.srt.sd77.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface INhanVienRepository extends JpaRepository<NhanVien, Long> {

    @Query(value = """
                select * from nhan_vien v
                WHERE trang_thai like :trangThai 
                AND (ma like :key
                OR ten like :key)
                ORDER BY ngay_tao DESC
            """, nativeQuery = true)
    Page<NhanVien> findEmployees(Pageable pageable, @Param("trangThai") String trangThai, @Param("key") String key);

    @Query(value = """
                select * from nhan_vien v
                where v.id = :id 
            """, nativeQuery = true)
    NhanVien findEmployeeByID(@Param("id") Long id);

    @Query(value = """
                select top 1 ma from nhan_vien order by ngay_tao desc
            """, nativeQuery = true)
    String generateNewestCode();

    @Query(value = """
                select * from nhan_vien
                where email = :email 
            """, nativeQuery = true)
    NhanVien findNhanVienByEmail(@Param("email") String email);

    @Query(value = """
                select * from nhan_vien
                where email = :email and mat_khau = :matKhau
            """, nativeQuery = true)
    NhanVien findNhanVienByEmailAndPass(@Param("email") String email, @Param("matKhau") String pass);

}
