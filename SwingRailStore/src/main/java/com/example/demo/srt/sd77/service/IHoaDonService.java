package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.HoaDon;
import com.example.demo.srt.sd77.entity.KhachHang;
import com.example.demo.srt.sd77.entity.Voucher;
import com.example.demo.srt.sd77.entity.request.ProductVoucherUpdateRequest;
import com.example.demo.srt.sd77.entity.responce.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Date;

public interface IHoaDonService {
    ArrayList<HoaDon> getHoaDonByTrangThai(Integer trangThai);

    HoaDon createBillWait();

    String generateCode();

    String deleteBillById(Long id);

    HoaDon addVoucherToBill(ProductVoucherUpdateRequest req);

    HoaDon updateHoaDon(HoaDon hoaDon);

    Voucher getVoucherByIdBill(Long id);

    KhachHang getCustomerByIdBill(Long id);

    Page<HoaDon> getBillAndPanigation(Integer pageNo, Integer pageSize, Integer state);

    Page<HoaDon> getBillAndPanigationByIdCustomer(Integer pageNo, Integer pageSize, Integer state, Long id);

    HoaDon getBillById(Long id);

    HoaDon getBillByCode(String code);

    RevenueResponce getRevenueMonth();

    RevenueResponce getRevenueDay();

    Integer getQuantityOfProductWithMonth();

    ArrayList<RevenueRangeDateResponce> getRevenueRangeDate(Date startDate, Date endDate);

    ArrayList<ProductBestSellerResponce> getBestSeller();

    ArrayList<BillStateResponce> getBillState();

    ArrayList<ProductBestSellerResponce> getTop5ProductBestSellerFillter(Integer state);

    ArrayList<RevenueFillterResponce> getRevenueFillter(Integer state);

    ArrayList<BillRevenueResponse> getQuantityBillByState();

    ArrayList<BillRevenueResponse> getQuantityBillWithState(Integer state);

    ArrayList<BillRevenueResponse> getQuantityBillByStateAndIdCustomer(Long id);

    HoaDon getNewBill();
}
