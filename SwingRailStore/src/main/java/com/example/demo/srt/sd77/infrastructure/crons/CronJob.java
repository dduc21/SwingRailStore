package com.example.demo.srt.sd77.infrastructure.crons;

import ch.qos.logback.core.util.StringUtil;
import com.example.demo.srt.sd77.entity.*;
import com.example.demo.srt.sd77.enums.StatusDotGiamGia;
import com.example.demo.srt.sd77.enums.StatusVoucher;
import com.example.demo.srt.sd77.infrastructure.configs.mail.EmailController;
import com.example.demo.srt.sd77.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
public class CronJob {

    private static final Logger logger = LoggerFactory.getLogger(CronJob.class);

    @Autowired
    private IVoucherRepository voucherRepo;

    @Autowired
    private IDotGiamGiaRepository dotGiamGiaRepo;

    private LocalDate lastRunDate = LocalDate.now();

    @Autowired
    private ISanPhamChiTietRepository sanPhamChiTietRepo;
    @Autowired
    private IKhachHangRepository khachHangRepository;

    @Autowired
    private EmailController emailController;

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
    @Scheduled(cron = "0 0 10 * * ?")
    public void sendDailyRankNotifications() {
        emailController.sendDailyRankNotification();
        System.out.println("abvasvjavnakvankaksvkaknsvnksavnka: 10h rồi!");
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDailyNotifications() {
        emailController.sendDailyNotification();
        System.out.println("abvasvjavnakvankaksvkaknsvnksavnka: 9h rồi!");
    }

//    @Scheduled(cron = "0 0 10 * * ?")
//    public void resetPointsOfCustomerAfter90days() {
//        LocalDate currentDate = LocalDate.now();
//
//        if (ChronoUnit.DAYS.between(lastRunDate, currentDate) >= 90) {
//            KhachHang kh = new KhachHang();
//            kh.setTichDiem(0);
//            khachHangRepository.save(kh);
//            emailController.sendDailyResetRankNotification();
//            lastRunDate = currentDate;
//            System.out.println("Email đã được gửi sau 90 ngày!");
//        } else {
//            System.out.println("Chưa đến thời gian gửi email!");
//        }
//    }

}
