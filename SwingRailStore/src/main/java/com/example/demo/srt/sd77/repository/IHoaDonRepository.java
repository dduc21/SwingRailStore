package com.example.demo.srt.sd77.repository;

import com.example.demo.srt.sd77.entity.HoaDon;
import com.example.demo.srt.sd77.entity.responce.BillRevenueResponse;
import com.example.demo.srt.sd77.entity.responce.BillStateResponce;
import com.example.demo.srt.sd77.entity.responce.ProductBestSellerResponce;
import com.example.demo.srt.sd77.entity.responce.ProductBestSellerResponse;
import com.example.demo.srt.sd77.entity.responce.RevenueFillterResponce;
import com.example.demo.srt.sd77.entity.responce.RevenueRangeDateResponce;
import com.example.demo.srt.sd77.entity.responce.RevenueResponce;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Date;

public interface IHoaDonRepository extends JpaRepository<HoaDon, Long> {

    @Query(value = """
                    select * from hoa_don where trang_thai = :trangThai and deleted = 1
            """, nativeQuery = true)
    ArrayList<HoaDon> getHoaDonByTrangThai(@Param("trangThai") Integer trangThai);

    @Query(value = """
                select top 1 ma from hoa_don order by ngay_tao desc
            """, nativeQuery = true)
    String generateNewestCode();

    @Modifying
    @Transactional
    @Query(value = """
                update hoa_don set deleted = 0 where id = :id
            """, nativeQuery = true)
    void deleteBillByIdBill(@Param("id") Long id);

    @Query(value = """
                    select * from hoa_don where not trang_thai = 0 and deleted = 1 order by ngay_tao desc
            """, nativeQuery = true)
    Page<HoaDon> getAllBillPanigation(Pageable pageable);

    @Query(value = """
                    select * from hoa_don where not trang_thai = 0 and deleted = 1 and id_khach_hang = :id order by ngay_tao desc
            """, nativeQuery = true)
    Page<HoaDon> getAllBillPanigationAllByIdCustomer(Pageable pageable, @Param("id") Long id);

    @Query(value = """
                    select * from hoa_don where trang_thai = :trangThai and deleted = 1 order by ngay_tao desc
            """, nativeQuery = true)
    Page<HoaDon> getBillPanigationByState(Pageable pageable, @Param("trangThai") Integer trangThai);

    @Query(value = """
                    select * from hoa_don where trang_thai = :trangThai and deleted = 1 and id_khach_hang = :id order by ngay_tao desc
            """, nativeQuery = true)
    Page<HoaDon> getBillPanigationByStateByIdCustomer(Pageable pageable, @Param("trangThai") Integer trangThai, @Param("id") Long id);

    @Query(value = """
                    select count(*) as so_luong, SUM(hd.tong_tien_sau_giam - hd.phi_van_chuyen) as tong_tien from hoa_don as hd
                    where deleted = 1 and not trang_thai in (0, 5, 6) and MONTH(hd.ngay_tao) = MONTH(GETDATE())
            """, nativeQuery = true)
    RevenueResponce getRevenueMonth();

    @Query(value = """
                    select count(*) as so_luong, SUM(hd.tong_tien_sau_giam - hd.phi_van_chuyen) as tong_tien from hoa_don as hd
                    where deleted = 1 and not trang_thai in (0, 5, 6) and CAST( hd.ngay_tao AS Date ) = CAST( GETDATE() AS Date )
            """, nativeQuery = true)
    RevenueResponce getRevenueDay();

    @Query(value = """
                    select sum(hdct.so_luong)  from hoa_don as hd
                    join hoa_don_chi_tiet hdct on hdct.id_hoa_don = hd.id
                    where hd.deleted = 1 and not hd.trang_thai in (0, 5, 6) and MONTH(hd.ngay_tao) = MONTH(GETDATE())
            """, nativeQuery = true)
    Integer getQuantityOfProductWithMonth();

    @Query(value = """
             select CAST( hd.ngay_tao AS Date ) as ngay ,count(*) as so_luong, SUM(hd.tong_tien_sau_giam - hd.phi_van_chuyen) as tong_tien
             from hoa_don as hd
             where deleted = 1 and not trang_thai in (0, 5, 6)
             and( CAST( hd.ngay_tao AS Date ) > CAST( :start_date AS Date )
              and CAST( hd.ngay_tao AS Date ) <= CAST( :end_date AS Date )
              )
             group by CAST( hd.ngay_tao AS Date )
            """, nativeQuery = true)
    ArrayList<RevenueRangeDateResponce> getRevenueRangeDate(@Param("start_date") Date startDate,
                                                            @Param("end_date") Date endDate);

    @Query(value = """
             select CAST( hd.ngay_tao AS Date ) as ngay ,count(*) as so_luong, SUM(hd.tong_tien_sau_giam - hd.phi_van_chuyen) as tong_tien
             from hoa_don as hd
             where deleted = 1 and not trang_thai in (0, 5, 6)
             group by CAST( hd.ngay_tao AS Date )
            """, nativeQuery = true)
    ArrayList<RevenueFillterResponce> getRevenueRangeDay();

    @Query(value = """
             select MONTH( hd.ngay_tao  ) as ngay ,count(*) as so_luong, SUM(hd.tong_tien_sau_giam - hd.phi_van_chuyen) as tong_tien
             from hoa_don as hd
             where deleted = 1 and not trang_thai in (0, 5, 6)
             group by MONTH( hd.ngay_tao  )
            """, nativeQuery = true)
    ArrayList<RevenueFillterResponce> getRevenueRangeMonth();

    @Query(value = """
             select YEAR( hd.ngay_tao  ) as ngay ,count(*) as so_luong, SUM(hd.tong_tien_sau_giam - hd.phi_van_chuyen) as tong_tien
             from hoa_don as hd
             where deleted = 1 and not trang_thai in (0, 5, 6)
             group by YEAR( hd.ngay_tao  )
            """, nativeQuery = true)
    ArrayList<RevenueFillterResponce> getRevenueRangeYear();

    @Query(value = """
             select top 5 sp.ten ,sum(hdct.so_luong) as so_luong, SUM(hd.tong_tien_sau_giam - hd.phi_van_chuyen) as tong_tien
             from hoa_don as hd
             join hoa_don_chi_tiet hdct on hdct.id_hoa_don = hd.id
             join san_pham_chi_tiet spct on spct.id = hdct.id_san_pham_chi_tiet
             join san_pham sp on sp.id = spct.id_san_pham
             where hd.deleted = 1 and not hd.trang_thai in (0, 5, 6)
             group by sp.ten
             order by so_luong desc
            """, nativeQuery = true)
    ArrayList<ProductBestSellerResponce> getTop5ProductBestSeller();

    @Query(value = """
                 select hd.trang_thai,count(hd.id) as so_luong
            	 from hoa_don as hd
            	 join hoa_don_chi_tiet hdct on hdct.id_hoa_don = hd.id
            	 where hd.deleted = 1 and not hd.trang_thai in (0, 5, 6)
            	 group by hd.trang_thai
            	 order by so_luong desc
            """, nativeQuery = true)
    ArrayList<BillStateResponce> getBillState();

    @Query(value = """
                 select top 5 sp.ten ,sum(hdct.so_luong) as so_luong, SUM(hd.tong_tien_sau_giam - hd.phi_van_chuyen) as tong_tien
                 from hoa_don as hd
                 join hoa_don_chi_tiet hdct on hdct.id_hoa_don = hd.id
                 join san_pham_chi_tiet spct on spct.id = hdct.id_san_pham_chi_tiet
                 join san_pham sp on sp.id = spct.id_san_pham
                 where hd.deleted = 1 and not hd.trang_thai in (0, 5, 6)
            	 and CAST( hd.ngay_tao AS Date ) = CAST( getdate() AS Date )
                 group by sp.ten
                 order by so_luong desc
            """, nativeQuery = true)
    ArrayList<ProductBestSellerResponce> getTop5ProductBestSellerDay();

    @Query(value = """
                 select top 5 sp.ten ,sum(hdct.so_luong) as so_luong, SUM(hd.tong_tien_sau_giam - hd.phi_van_chuyen) as tong_tien
                 from hoa_don as hd
                 join hoa_don_chi_tiet hdct on hdct.id_hoa_don = hd.id
                 join san_pham_chi_tiet spct on spct.id = hdct.id_san_pham_chi_tiet
                 join san_pham sp on sp.id = spct.id_san_pham
                 where hd.deleted = 1 and not hd.trang_thai in (0, 5, 6)
            	 and MONTH( hd.ngay_tao ) = MONTH( getdate())
                 group by sp.ten
                 order by so_luong desc
            """, nativeQuery = true)
    ArrayList<ProductBestSellerResponce> getTop5ProductBestSellerMonth();

    @Query(value = """
                 select top 5 sp.ten ,sum(hdct.so_luong) as so_luong, SUM(hd.tong_tien_sau_giam - hd.phi_van_chuyen) as tong_tien
                 from hoa_don as hd
                 join hoa_don_chi_tiet hdct on hdct.id_hoa_don = hd.id
                 join san_pham_chi_tiet spct on spct.id = hdct.id_san_pham_chi_tiet
                 join san_pham sp on sp.id = spct.id_san_pham
                 where hd.deleted = 1 and not hd.trang_thai in (0, 5, 6)
            	 and YEAR( hd.ngay_tao ) = YEAR( getdate())
                 group by sp.ten
                 order by so_luong desc
            """, nativeQuery = true)
    ArrayList<ProductBestSellerResponce> getTop5ProductBestSellerYear();

    @Query(value = """
                    select hd.trang_thai, count(hd.id)as so_luong from hoa_don hd
                     group by hd.trang_thai
            """, nativeQuery = true)
    ArrayList<BillRevenueResponse> getQuantityBillByStates();

    @Query(value = """
                    select hd.trang_thai, count(hd.id)as so_luong from hoa_don hd
                    where hd.loai_hoa_don = :typeBill
                     group by hd.trang_thai
            """, nativeQuery = true)
    ArrayList<BillRevenueResponse> getQuantityBillByBuyPaymentState(@Param("typeBill") Integer typeBill);

    @Query(value = """
                    select hd.trang_thai, count(hd.id)as so_luong from hoa_don hd
                    where id_khach_hang = :id_khach_hang
                    group by hd.trang_thai
            """, nativeQuery = true)
    ArrayList<BillRevenueResponse> getQuantityBillByStatesAndIDCustomer(@Param("id_khach_hang") Long id_khach_hang);

    @Query(value = """
                select sp.id ,sum(hdct.so_luong) as so_luong, SUM(hd.tong_tien_sau_giam - hd.phi_van_chuyen) as tong_tien
                 from hoa_don as hd
                 join hoa_don_chi_tiet hdct on hdct.id_hoa_don = hd.id
                 join san_pham_chi_tiet spct on spct.id = hdct.id_san_pham_chi_tiet
                 join san_pham sp on sp.id = spct.id_san_pham
                 where hd.deleted = 1 and not hd.trang_thai in (0, 5, 6)
            	 and YEAR( hd.ngay_tao ) = YEAR( getdate())
                 group by sp.id
                 order by so_luong desc
            """, nativeQuery = true)
    ArrayList<ProductBestSellerResponse> getTopProductBestSeller();


    @Query(value = """
            select * from hoa_don where ma = :ma
            """, nativeQuery = true)
    HoaDon getHoaDonByMa(@Param("ma") String ma);
}
