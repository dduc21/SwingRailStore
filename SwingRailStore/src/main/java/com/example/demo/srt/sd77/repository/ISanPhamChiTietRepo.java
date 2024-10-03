package com.example.demo.srt.sd77.repository;

import com.example.demo.srt.sd77.entity.DotGiamGia;
import com.example.demo.srt.sd77.entity.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ISanPhamChiTietRepo extends JpaRepository<SanPhamChiTiet, Long> {

    @Query( value = """
        select * from san_pham_chi_tiet v
        where id_san_pham = :id
        and id_kich_co like :id_kich_co
        and id_mau_sac like :id_mau_sac
        ORDER BY ngay_tao DESC
    """, nativeQuery = true)
    Page<SanPhamChiTiet> findPanigation(Pageable pageable,
                                        @Param("id") Long id,
                                        @Param("id_kich_co")String idKichCo,
                                        @Param("id_mau_sac")String idMauSac);

    @Query(value = """
        select * from san_pham_chi_tiet v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<SanPhamChiTiet> findProductDetailById(@Param("id") Long id);

    ArrayList<SanPhamChiTiet> findByOrderByNgayTaoDesc();

    @Query( value = """
        select * from san_pham_chi_tiet v
        ORDER BY ngay_tao DESC
    """, nativeQuery = true)
    Page<SanPhamChiTiet> findAllAndPanigation(Pageable pageable);

    @Query(value = """  
            select        spct.[deleted]
                         ,spct.[don_gia]
                         ,spct.[gia_tri_giam]
                         ,spct.[so_luong_ton]
                         ,spct.[trang_thai]
                         ,spct.[trong_luong]
                         ,spct.[id]
                         ,spct.[id_dot_giam_gia]
                         ,spct.[id_kich_co]
                         ,spct.[id_mau_sac]
                         ,spct.[id_san_pham]
                         ,spct.[ngay_cap_nhat]
                         ,spct.[ngay_tao]
                         ,spct.[QRcode] from san_pham_chi_tiet spct
            join san_pham sp on sp.id = spct.id_san_pham
            where sp.id in :ids
            and id_mau_sac like :idMauSac
            and id_kich_co like :idKichCo 
            and sp.id_the_loai like :idTheLoai 
            and sp.ten like :key
            ORDER BY spct.ngay_tao DESC
    """, nativeQuery = true)
    Page<SanPhamChiTiet> getProductDetailByIds(@Param("ids") List<Integer> ids,
                                               @Param("idMauSac") String idMauSac,
                                               @Param("idKichCo") String idKichCo,
                                               @Param("idTheLoai") String idTheLoai,
                                               @Param("key") String key,
                                               Pageable pageable);

    @Query(value = """
        select * from san_pham_chi_tiet where deleted = 1 and id_dot_giam_gia = :id
        """, nativeQuery = true)
    ArrayList<SanPhamChiTiet> getProductDetailByIdSale(@Param("id") Long id);

    @Query(value = """
        select * from san_pham_chi_tiet spct
        where spct.id_san_pham = :id
        """, nativeQuery = true)
    ArrayList<SanPhamChiTiet> getProductDetailsByIdProduct(@Param("id") Long id);

    @Query(value = """
        select * from san_pham_chi_tiet
        where id_dot_giam_gia = :id
    """, nativeQuery = true)
    ArrayList<SanPhamChiTiet> getProductDetailsByIdSale(@Param("id") Long id);

    @Query(value = """
        select spct.* from san_pham_chi_tiet spct
        join dot_giam_gia sale on sale.id = spct.id_dot_giam_gia
    """, nativeQuery = true)
    ArrayList<SanPhamChiTiet> getTopProductOfSales();

}
