package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.entity.HoaDon;
import com.example.demo.srt.sd77.entity.request.ProductVoucherUpdateRequest;
import com.example.demo.srt.sd77.service.impl.HoaDonServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/bill")
@CrossOrigin("*")
public class BillRestController {

    @Autowired
    private HoaDonServiceImpl hoaDonService;

    @GetMapping("/get-bill-by-state/{state}")
    public ArrayList<HoaDon> getHoaDonByTrangThai(@PathVariable("state") Integer trangThai) {
        return hoaDonService.getHoaDonByTrangThai(trangThai);
    }

    @PostMapping("/create-bill-wait")
    public HoaDon createHoaDon() {
        return hoaDonService.createBillWait();
    }

    @DeleteMapping("/delete-bill/{id}")
    public ResponseEntity<?> deleteHoaDon(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(hoaDonService.deleteBillById(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/add-voucher-to-bill")
    public ResponseEntity<?> addVoucherToBill(@RequestBody ProductVoucherUpdateRequest req) {
        try {
            return new ResponseEntity<>(hoaDonService.addVoucherToBill(req), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update-bill")
    public ResponseEntity<?> updateHoaDon(@RequestBody HoaDon hoaDon) {
        try {
            return new ResponseEntity<>(hoaDonService.updateHoaDon(hoaDon), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-voucher-by-id/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(hoaDonService.getVoucherByIdBill(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-customer-by-id/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(hoaDonService.getCustomerByIdBill(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-bill-panigation")
    public ResponseEntity<?> getBillById(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("state") Integer trangThai
    ) {
        try {
            return new ResponseEntity<>(hoaDonService.getBillAndPanigation(page, size, trangThai), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-bill-panigation-by-id-customer")
    public ResponseEntity<?> getBillsByIdCustomer(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("state") Integer trangThai,
            @RequestParam("id") Long id
    ) {
        try {
            return new ResponseEntity<>(hoaDonService.getBillAndPanigationByIdCustomer(page, size, trangThai, id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-bill/{id}")
    public ResponseEntity<?> getBillById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(hoaDonService.getBillById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-bill-by-code/{code}")
    public ResponseEntity<?> getBillByCode(@PathVariable("code") String code) {
        try {
            return new ResponseEntity<>(hoaDonService.getBillByCode(code), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-revenue-month")
    public ResponseEntity<?> getRevenueMonth() {
        return new ResponseEntity<>(hoaDonService.getRevenueMonth(), HttpStatus.OK);
    }

    @GetMapping("/get-revenue-day")
    public ResponseEntity<?> getRevenueDay() {
        return new ResponseEntity<>(hoaDonService.getRevenueDay(), HttpStatus.OK);
    }

    @GetMapping("/get-quantity-of-product-with-month")
    public ResponseEntity<?> getQuantityOfProductWithMonth() {
        return new ResponseEntity<>(hoaDonService.getQuantityOfProductWithMonth(), HttpStatus.OK);
    }

    @GetMapping("/get-revenue-range-date")
    public ResponseEntity<?> getRevenueRangeDate(@RequestParam("startDate") String startDate,
                                                 @RequestParam("endDate") String endDate) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date start = df.parse(startDate);
            Date end = df.parse(endDate);
            return new ResponseEntity<>(hoaDonService.getRevenueRangeDate(start, end), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/get-best-seller")
    public ResponseEntity<?> getBestSeller() {
        return new ResponseEntity<>(hoaDonService.getBestSeller(), HttpStatus.OK);
    }

    @GetMapping("/get-bill-state")
    public ResponseEntity<?> getBillState() {
        try {
            return new ResponseEntity<>(hoaDonService.getBillState(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-top5-product-best-seller")
    public ResponseEntity<?> getTop5ProductBestSeller(@RequestParam("state") Integer state) {
        try {
            return new ResponseEntity<>(hoaDonService.getTop5ProductBestSellerFillter(state), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-revenue-fillter")
    public ResponseEntity<?> getRevenueFillter(@RequestParam("state") Integer state) {
        try {
            return new ResponseEntity<>(hoaDonService.getRevenueFillter(state), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-quantity-bills")
    public ResponseEntity<?> getQuantityBill() {
        try {
            return new ResponseEntity<>(hoaDonService.getQuantityBillByState(), HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/get-quantity-bills-state/{state}")
    public ResponseEntity<?> getQuantityBillBuyState(@PathVariable("state") Integer state) {
        try {
            return new ResponseEntity<>(hoaDonService.getQuantityBillWithState(state), HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/get-quantity-bills-by-id-customer/{id}")
    public ResponseEntity<?> getQuantityBillAndIdCustomer(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(hoaDonService.getQuantityBillByStateAndIdCustomer(id), HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/get-new-bill")
    public ResponseEntity<?> getNewBill() {
        try {
            return new ResponseEntity<>(hoaDonService.getNewBill(), HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
