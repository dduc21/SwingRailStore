package com.example.demo.srt.sd77.repository;

import com.example.demo.srt.sd77.entity.MauSac;
import com.example.demo.srt.sd77.entity.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ITheLoaiRepo extends JpaRepository<TheLoai, Long> {

    @Query( value = """
        select * from the_loai v
        WHERE ten like :key
        ORDER BY ngay_tao DESC
    """, nativeQuery = true)
    Page<TheLoai> findPanigation(Pageable pageable, @Param("key") String key);

    @Query(value = """
        select * from the_loai v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<TheLoai> findTypeById(@Param("id") Long id);

    @Query(value = """
        select * from the_loai v
        where v.ten = :ten 
    """, nativeQuery = true)
    ArrayList<TheLoai> findTypeByName(@Param("ten") String ten);

    ArrayList<TheLoai> findByOrderByNgayTaoDesc();

    @Query(value = """
        select id_the_loai, tl.ten, count(sp.id) as so_luong from san_pham sp
        join the_loai tl on tl.id = sp.id_the_loai
        group by id_the_loai, tl.ten
    """, nativeQuery = true)
    ArrayList<Object> getQuantitysOfProductByType();

}
