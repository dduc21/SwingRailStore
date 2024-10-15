package com.example.demo.srt.sd77.repository;

import com.example.demo.srt.sd77.entity.SanPham;
import com.example.demo.srt.sd77.entity.responce.ProductResponce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface ISanPhamRepository extends JpaRepository<SanPham, Long> {

    @Query( value = """
         select sp.id, sp.ten, sp.mo_ta, tl.ten as 'ten_the_loai',
          th.ten as 'ten_thuong_hieu',
          sum(spct.so_luong_ton) as 'so_luong',
          sp.trang_thai
          from san_pham sp
          join the_loai tl on tl.id = sp.id_the_loai
          join thuong_hieu th on th.id = sp.id_thuong_hieu
          join san_pham_chi_tiet spct on spct.id_san_pham = sp.id
          WHERE sp.ten like :key and sp.id_the_loai like :id_type and sp.id_thuong_hieu like :id_brand
          group by sp.id,sp.id, sp.ten, sp.mo_ta, tl.ten, th.ten, sp.trang_thai
    """, nativeQuery = true)
    Page<ProductResponce> findPanigation(Pageable pageable,
                                         @Param("key") String key,
                                         @Param("id_type") String idType,
                                         @Param("id_brand") String idBrand);

    @Query(value = """
        select * from san_pham v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<SanPham> findProductById(@Param("id") Long id);

    @Query(value = """
        select * from san_pham v
        where v.ten = :ten 
    """, nativeQuery = true)
    ArrayList<SanPham> findProductByName(@Param("ten") String ten);

    ArrayList<SanPham> findByOrderByNgayTaoDesc();

    @Query(value = """
        select top 1 ma from san_pham order by ngay_tao desc
    """, nativeQuery = true)
    String generateNewestCode();

    @Query(value = """
       select MAX(don_gia) from san_pham_chi_tiet
    """, nativeQuery = true)
    BigDecimal getMaxDonGia();

}
