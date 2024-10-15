package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.entity.KhachHang;
import com.example.demo.srt.sd77.entity.request.CustomerAddRequest;
import com.example.demo.srt.sd77.entity.request.CustomerRegisterRequest;
import com.example.demo.srt.sd77.infrastructure.configs.mail.EmailService;
import com.example.demo.srt.sd77.service.impl.KhachHangServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class CustomerRestController {

    private final EmailService emailService;

    @Autowired
    private KhachHangServiceImpl customerSer;

    @Autowired
    private CustomerRestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getAllCustomers(@RequestParam("page") Integer pageNo,
                                         @RequestParam("size") Integer pageSize,
                                         @RequestParam("key") String key,
                                         @RequestParam("trang_thai") String trangThai) {
        try {
            return new ResponseEntity<>(customerSer.getCustomersWithPanigation(pageNo, pageSize, key, trangThai), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/find-all-ranking-customers")
    public ResponseEntity<?> getAllRankingCustomers() {
        try {
            return new ResponseEntity<>(customerSer.rankingCustomer(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestBody CustomerAddRequest req) {
        try {
            return new ResponseEntity<>(customerSer.addCustomer(req), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-customer/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(customerSer.getCustomerById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-points")
    public ResponseEntity<?> updateCustomerPoints(@RequestBody KhachHang khachHang) {
        try {
            KhachHang updatedCustomer = customerSer.updatePoints(khachHang.getId(), khachHang.getTichDiem());
            if (updatedCustomer != null) {
                Context context = new Context();
                context.setVariable("name", updatedCustomer.getTen());
                context.setVariable("pointsAdded", khachHang.getTichDiem());
                context.setVariable("totalPoints", updatedCustomer.getTichDiem());

                emailService.sendEmailWithHtmlTemplate(
                        updatedCustomer.getEmail(),
                        "Cập nhật điểm tích lũy",
                        "points-update-email-template",
                        context
                );

                // Trả về kết quả thành công
                return ResponseEntity.ok(updatedCustomer);
            } else {
                return ResponseEntity.status(404).body("Khách hàng không tồn tại");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật điểm");
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody KhachHang khachHang) {
        try {
            return new ResponseEntity<>(customerSer.updateCustomer(khachHang), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegisterRequest req) {
        try {
            customerSer.register(req);
            Context context = new Context();
            context.setVariable("name", req.getTen());

            emailService.sendEmailWithHtmlTemplate(req.getEmail(),
                    "Chào mừng đến với hệ thống của chúng tôi!",
                    "welcome-email-template",
                    context);

            return ResponseEntity.ok("Account registered successfully.");
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam("user") String username, @RequestParam("pass") String password) {
        try {
            return new ResponseEntity<>(customerSer.login(username, password), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change-pass")
    public ResponseEntity<?> changePass(@RequestParam("id") String id, @RequestParam("pass") String password) {
        try {
            customerSer.changePass(password, id);
            return new ResponseEntity<>("Thành công", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
