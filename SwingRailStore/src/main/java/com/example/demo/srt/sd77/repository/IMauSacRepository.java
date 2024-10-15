package com.example.demo.srt.sd77.repository;

import com.example.demo.srt.sd77.entity.MauSac;
import com.example.demo.srt.sd77.entity.responce.ColorIdentityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IMauSacRepository extends JpaRepository<MauSac, Long> {


    @Query( value = """
        select * from mau_sac v
        WHERE ten like :key
        ORDER BY ngay_tao DESC
    """, nativeQuery = true)
    Page<MauSac> findColorPanigation(Pageable pageable, @Param("key") String key);

    @Query(value = """
        select * from mau_sac v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<MauSac> findColorById(@Param("id") Long id);

    @Query(value = """
        select * from mau_sac v
        where v.ten = :ten 
    """, nativeQuery = true)
    ArrayList<MauSac> findColorByName(@Param("ten") String ten);

    @Query(value = """
            select ms.id, ms.ten from san_pham_chi_tiet spct
            join san_pham sp on sp.id = spct.id_san_pham
            join mau_sac ms on ms.id = spct.id_mau_sac
            where sp.id = :id_san_pham
            group by ms.id, ms.ten
    """, nativeQuery = true)
    ArrayList<ColorIdentityResponse> getColorIdentity(@Param("id_san_pham") Long id_san_pham);

    @Query(value = """
       select ms.id, ms.ten, count(spct.id) from san_pham_chi_tiet spct
          join mau_sac ms on ms.id  = spct.id_mau_sac
          group by ms.id, ms.ten
    """, nativeQuery = true)
    ArrayList<Object> getQuantitysOfProductByColor();
}
