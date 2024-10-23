package com.example.demo.srt.sd77.infrastructure.configs.mail;

import com.example.demo.srt.sd77.entity.*;
import com.example.demo.srt.sd77.entity.request.VoucherDetailRequest;
import com.example.demo.srt.sd77.repository.IHoaDonChiTietRepository;
import com.example.demo.srt.sd77.repository.IKhachHangRepository;
import com.example.demo.srt.sd77.repository.INhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    private IHoaDonChiTietRepository hoaDonChiTietRepo;

    @Autowired
    private IKhachHangRepository khachHangRepository;


    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public INhanVienRepository nhanVienRepo;

    @PostMapping("/send-email")
    public String sendEmail(@RequestBody HoaDon hoaDon) {
        String pattern = "HH:mm dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Context context = new Context();
        ArrayList<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepo.findBillDetailsByCodeBill(hoaDon.getMa());
        String date = simpleDateFormat.format(hoaDonChiTiets.get(0).getNgayTao());
        String trangThaiHoaDon = hoaDon.getTrangThai() == 1 ? "Tạo hóa đơn" : hoaDon.getTrangThai() == 2 ? "Chờ giao" : hoaDon.getTrangThai() == 3 ? "Đang giao" : hoaDon.getTrangThai() == 4 ? "Thành công" : hoaDon.getTrangThai() == 5 ? "Bị hủy" : "Trả hàng";

        context.setVariable("bill", hoaDon);
        context.setVariable("billDetails", hoaDonChiTiets);
        context.setVariable("ngayTao", date);
        context.setVariable("trangThaiHoaDon", trangThaiHoaDon);

        if (hoaDon.getTrangThai() == 1) {
            emailService.sendEmailWithHtmlTemplate(hoaDon.getEmail(), "Đơn hàng " + hoaDon.getMa() + "  được tạo thành công!", "bill-template", context);
        } else if (hoaDon.getTrangThai() == 2) {
            emailService.sendEmailWithHtmlTemplate(hoaDon.getEmail(), "Đơn hàng " + hoaDon.getMa() + " đang được người bán giao cho bên vận chuyển!", "bill-template", context);
        } else if (hoaDon.getTrangThai() == 3) {
            emailService.sendEmailWithHtmlTemplate(hoaDon.getEmail(), "Đơn hàng " + hoaDon.getMa() + " đang trên đường giao đến bạn! ", "bill-template", context);
        } else if (hoaDon.getTrangThai() == 4) {
            emailService.sendEmailWithHtmlTemplate(hoaDon.getEmail(), "Đơn hàng " + hoaDon.getMa() + " đã hoàn thành!", "bill-template", context);
        } else if (hoaDon.getTrangThai() == 5) {
            emailService.sendEmailWithHtmlTemplate(hoaDon.getEmail(), "Đơn hàng " + hoaDon.getMa() + " đã bị hủy!", "bill-template", context);
        } else if (hoaDon.getTrangThai() == 6) {
            emailService.sendEmailWithHtmlTemplate(hoaDon.getEmail(), "Đơn hàng " + hoaDon.getMa() + " đã được trả lại!", "bill-template", context);
        }
        return "Email sent successfully!";
    }

    @PostMapping("/send-html-voucher-email")
    public ResponseEntity<String> sendVoucherEmail(@RequestBody VoucherDetailRequest request) {
        try {
            Context context = new Context();
            KhachHang khachHang = request.getKhachHang();
            Voucher voucher = request.getVoucher();

            context.setVariable("fullNameCustomer", khachHang.getTen());
            context.setVariable("voucherName", voucher.getTen());
            context.setVariable("voucherCode", voucher.getMa());

            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String formattedGiaTriToiThieu = currencyFormatter.format(voucher.getGiaTriToiThieu());

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            String formattedNgayBatDau = dateFormat.format(voucher.getNgayBatDau());
            String formattedNgayKetThuc = dateFormat.format(voucher.getNgayKetThuc());

            context.setVariable("valueVoucher", voucher.getPhanTramGiam() + "%");
            context.setVariable("conditionVoucher", formattedGiaTriToiThieu);
            context.setVariable("startDate", formattedNgayBatDau);
            context.setVariable("endDate", formattedNgayKetThuc);

            emailService.sendEmailWithHtmlTemplate(khachHang.getEmail(), "Thông báo voucher mới từ Swing Rail Store", "voucher-email-template", context);
            System.out.println("aa");

            return ResponseEntity.ok("Voucher email sent successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send voucher email.");
        }
    }

    @PostMapping("/confirm-password-reset")
    public ResponseEntity<String> confirmPasswordReset(@RequestParam Map<String, String> requestBody) {
        try {

            NhanVien account = nhanVienRepo.findNhanVienByEmail(requestBody.get("email"));
            account.setMatKhau("123456");
            nhanVienRepo.save(account);

            Context context = new Context();
            context.setVariable("newPassword", "123456");

            emailService.sendEmailWithHtmlTemplate(requestBody.get("email"), "Mật khẩu đã được đặt lại", "password-reset-notification-template",  // Template email thông báo
                    context);
            return ResponseEntity.ok("200");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send password reset notification email.");
        }
    }


    public void sendDailyRankNotification() {
        try {
            List<KhachHang> customers = khachHangRepository.findAll();
            StringBuilder resultMessage = new StringBuilder();

            for (KhachHang temp : customers) {
                int points = temp.getTichDiem();
                String rank = emailService.calculateRank(points);
                String discount = emailService.getDiscountByRank(rank);

                Context context = new Context();
                context.setVariable("customerName", temp.getTen());
                context.setVariable("points", points);
                context.setVariable("rank", rank);
                context.setVariable("discount", discount);

                emailService.sendEmailWithHtmlTemplate(temp.getEmail(), "Thông báo điểm và hạng khách hàng", "daily-rank-notification-template", context);

                resultMessage.append("Đã gửi email cho ").append(temp.getTen()).append("\n");
                System.out.println(resultMessage);
            }

        } catch (Exception e) {
            System.err.println("Lỗi khi gửi email cho khách hàng " + e.getMessage());
        }
    }

    public void sendDailyResetRankNotification() {
        try {
            List<KhachHang> customers = khachHangRepository.findAll();
            StringBuilder resultMessage = new StringBuilder();

            for (KhachHang temp : customers) {
                Context context = new Context();
                context.setVariable("customerName", temp.getTen());

                emailService.sendEmailWithHtmlTemplate(temp.getEmail(), "Thông báo reset điểm!!", "daily-reset-rank-notification-template", context);

                resultMessage.append("Đã gửi email cho ").append(temp.getTen()).append("\n");
                System.out.println(resultMessage);
            }

        } catch (Exception e) {
            System.err.println("Lỗi khi gửi email cho khách hàng " + e.getMessage());
        }
    }

    public void sendDailyNotification() {
        try {
            List<KhachHang> customers = khachHangRepository.findAll();
            StringBuilder resultMessage = new StringBuilder();

            for (KhachHang temp : customers) {
                Context context = new Context();
                context.setVariable("customerName", temp.getTen());
                emailService.sendEmailWithHtmlTemplate(temp.getEmail(), "SWING RAIL STORE CHÀO BUỔI SÁNG!!", "daily-notification-template", context);

                resultMessage.append("Đã gửi email cho ").append(temp.getTen()).append("\n");
                System.out.println(resultMessage);
            }

        } catch (Exception e) {
            System.err.println("Lỗi khi gửi email cho khách hàng " + e.getMessage());
        }
    }
}
