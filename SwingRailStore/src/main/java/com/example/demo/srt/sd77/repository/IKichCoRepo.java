package com.example.demo.srt.sd77.repository;

import com.example.demo.srt.sd77.entity.KichCo;
import com.example.demo.srt.sd77.entity.responce.SizeIndetityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IKichCoRepo extends JpaRepository<KichCo, Long> {

    @Query(value = """
                select * from kich_co v
                WHERE kich_co like :key
                ORDER BY ngay_tao DESC
            """, nativeQuery = true)
    Page<KichCo> findSizePanigation(Pageable pageable, @Param("key") String key);

    @Query(value = """
                select * from kich_co v
                where v.id = :id 
            """, nativeQuery = true)
    ArrayList<KichCo> findSizeById(@Param("id") Long id);

    @Query(value = """
                select * from kich_co v
                where v.kich_co = :kich_co 
            """, nativeQuery = true)
    ArrayList<KichCo> findSizeBySize(@Param("kich_co") Integer kichCo);

    @Query(value = """
                    select kc.id, kc.kich_co from san_pham_chi_tiet spct
                    join san_pham sp on sp.id = spct.id_san_pham
                    join kich_co kc on kc.id = spct.id_kich_co
                    where sp.id = :id_san_pham
                    group by kc.id, kc.kich_co
            """, nativeQuery = true)
    ArrayList<SizeIndetityResponse> getSizeIndetity(@Param("id_san_pham") Long id_san_pham);

    @Query(value = """
                select kc.id, kc.kich_co, count(spct.id) from san_pham_chi_tiet spct
                join kich_co kc on kc.id = spct.id_kich_co
                group by kc.id, kc.kich_co
            """, nativeQuery = true)
    ArrayList<Object> getQuantitysOfProductBySize();
}
