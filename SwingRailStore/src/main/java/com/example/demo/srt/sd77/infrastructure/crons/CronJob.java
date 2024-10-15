package com.example.demo.srt.sd77.infrastructure.crons;

import com.example.demo.srt.sd77.entity.DotGiamGia;
import com.example.demo.srt.sd77.entity.PhieuGiamGiaChiTiet;
import com.example.demo.srt.sd77.entity.SanPhamChiTiet;
import com.example.demo.srt.sd77.entity.Voucher;
import com.example.demo.srt.sd77.enums.StatusDotGiamGia;
import com.example.demo.srt.sd77.enums.StatusVoucher;
import com.example.demo.srt.sd77.repository.IDotGiamGiaRepository;
import com.example.demo.srt.sd77.repository.ISanPhamChiTietRepository;
import com.example.demo.srt.sd77.repository.IVoucherRepository;
import com.example.demo.srt.sd77.repository.PhieuGiamGiaChiTietRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CronJob {

    private static final Logger logger = LoggerFactory.getLogger(CronJob.class);

    @Autowired
    private IVoucherRepository voucherRepo;

    @Autowired
    private IDotGiamGiaRepository dotGiamGiaRepo;

    @Autowired
    private ISanPhamChiTietRepository sanPhamChiTietRepo;

    @Autowired
    private PhieuGiamGiaChiTietRepository phieuGiamGiaChiTietRepository;

    @Scheduled(fixedRate = 1000)
    public void cronJobVoucher() {
        Date today = new Date();
        List<Voucher> vouchers = voucherRepo.findAll();
        vouchers.forEach(voucher -> {
            if (voucher.getNgayBatDau().after(today)) {
                voucher.setTrangThai(StatusVoucher.CHUA_BAT_DAU.getTrangThai());
            } else if (voucher.getNgayKetThuc().before(today)) {
                voucher.setTrangThai(StatusVoucher.KET_THUC.getTrangThai());
                // khi phieu giam gia ket thuc thi lap tuc nhung kh co voucher nay se mat ap dung

                List<PhieuGiamGiaChiTiet> voucherDetails = phieuGiamGiaChiTietRepository.getChiTietByPhieuGiamGia(voucher.getId());
                voucherDetails.forEach(voucherDetail -> {
                    phieuGiamGiaChiTietRepository.deleteById(voucherDetail.getId());
                });
            } else {
                voucher.setTrangThai(StatusVoucher.DANG_DIEN_RA.getTrangThai());
            }
            voucherRepo.save(voucher);

        });
    }

    @Scheduled(fixedRate = 1000)
    public void cronJobSale() {
        Date today = new Date();
        List<DotGiamGia> sales = dotGiamGiaRepo.findAll();
        sales.forEach(sale -> {
            if (sale.getNgayBatDau().after(today)) {
                sale.setTrangThai(StatusDotGiamGia.CHUA_BAT_DAU.getTrangThai());
            } else if (sale.getNgayKetThuc().before(today)) {
                sale.setTrangThai(StatusDotGiamGia.KET_THUC.getTrangThai());
                // Kh dot giam gia ket thuc lap tuc xoa tat ca cac sanpham co ap dung
                List<SanPhamChiTiet> productDetails = sanPhamChiTietRepo.getProductDetailsByIdSale(sale.getId());
                productDetails.forEach(productDetail -> {
                    productDetail.setIdDotGiamGia(null);
                    sanPhamChiTietRepo.save(productDetail);
                });
            } else {
                sale.setTrangThai(StatusDotGiamGia.DANG_DIEN_RA.getTrangThai());
            }
            dotGiamGiaRepo.save(sale);
        });
    }

}
