package com.example.demo.srt.sd77.repository;

import com.example.demo.srt.sd77.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IVoucherRepository extends JpaRepository<Voucher, Long> {

    @Query( value = """
        select * from Voucher v
        WHERE trang_thai like :trangThai 
        AND (ma like :key
        OR ten like :key)
        ORDER BY ngay_tao DESC
    """, nativeQuery = true)
    Page<Voucher> findVouchersByState(Pageable pageable, @Param("trangThai") String trangThai, @Param("key") String key);

    @Query(value = """
        select * from Voucher v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<Voucher> findVoucherByIdAndState(@Param("id") Long id);

    @Query(value = """
        select top 1 ma from voucher order by ngay_tao desc
    """, nativeQuery = true)
    String generateNewestCode();

    @Query(value = """
        where ma like :key
    """, nativeQuery = true)
    ArrayList<Voucher> getVouchersByKey(@Param("key") String key);

    @Query(value = """
        select * from voucher
    """, nativeQuery = true)
    ArrayList<Voucher> getVouchersModal();

    @Query(value = """
        select * from Voucher v
        where ma = :key
    """, nativeQuery = true)
    Voucher getVouchersByKeyAbsolute(@Param("key") String key);
}
