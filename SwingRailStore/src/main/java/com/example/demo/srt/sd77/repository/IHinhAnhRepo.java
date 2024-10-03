package com.example.demo.srt.sd77.repository;

import com.example.demo.srt.sd77.entity.HinhAnh;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IHinhAnhRepo extends JpaRepository<HinhAnh, Long> {

    @Query(value = """
                select * from hinh_anh v
                where id_san_pham_chi_tiet = :id
                ORDER BY ngay_tao DESC
            """, nativeQuery = true)
    Page<HinhAnh> findPanigation(Pageable pageable, Long id);

    @Query(value = """
                select * from hinh_anh v
                where v.id = :id 
            """, nativeQuery = true)
    ArrayList<HinhAnh> findImageById(@Param("id") Long id);

    @Query(value = """
                select * from hinh_anh v
                where v.id_san_pham_chi_tiet = :id
            """, nativeQuery = true)
    ArrayList<HinhAnh> findByOrderByNgayTaoDesc(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = """
                DELETE FROM hinh_anh WHERE id = :id
            """, nativeQuery = true)
    void deleteImageById(@Param("id") Long id);

    @Query(value = """
             select top 1 ha.duong_dan  from san_pham_chi_tiet spct
                 join hinh_anh ha on ha.id_san_pham_chi_tiet = spct.id
                 where spct.id_mau_sac = :id_mau_sac and spct.id_san_pham = :id_san_pham
            """, nativeQuery = true)
    String getPathImageByIdColorAndIdProduct(@Param("id_mau_sac") Long id_mau_sac, @Param("id_san_pham") Long id_san_pham);
}
