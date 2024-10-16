package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.HoaDon;
import com.example.demo.srt.sd77.entity.KhachHang;
import com.example.demo.srt.sd77.entity.Voucher;
import com.example.demo.srt.sd77.entity.request.ProductVoucherUpdateRequest;
import com.example.demo.srt.sd77.entity.responce.BillRevenueResponse;
import com.example.demo.srt.sd77.entity.responce.BillStateResponce;
import com.example.demo.srt.sd77.entity.responce.ProductBestSellerResponce;
import com.example.demo.srt.sd77.entity.responce.RevenueFillterResponce;
import com.example.demo.srt.sd77.entity.responce.RevenueRangeDateResponce;
import com.example.demo.srt.sd77.entity.responce.RevenueResponce;
import com.example.demo.srt.sd77.enums.StatusHoaDon;
import com.example.demo.srt.sd77.repository.IHoaDonRepository;
import com.example.demo.srt.sd77.service.IHoaDonChiTietService;
import com.example.demo.srt.sd77.service.IHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class HoaDonServiceImpl implements IHoaDonService {

    @Autowired
    private IHoaDonRepository hoaDonRepo;

    @Override
    public ArrayList<HoaDon> getHoaDonByTrangThai(Integer trangThai) {
        return (ArrayList<HoaDon>) hoaDonRepo.getHoaDonByTrangThai(trangThai);
    }

    @Override
    public HoaDon createBillWait() {

        ArrayList<HoaDon> orders = getHoaDonByTrangThai(0);
        if (orders.size() == 6) {
            throw new RuntimeException("Bạn chỉ có thể tạo tối đa 6 đơn hàng.");
        }

        HoaDon hoaDon = new HoaDon();

        hoaDon.setMa(generateCode());
        hoaDon.setTrangThai(StatusHoaDon.CHO.getTrangThai());
        return hoaDonRepo.save(hoaDon);
    }

    @Override
    public String generateCode() {
        String newestCode = hoaDonRepo.generateNewestCode();
        if (newestCode == null || newestCode.equals("")) {
            return "HD" + 0;
        }
        return "HD" + (Integer.parseInt(newestCode.substring(2)) + 1);
    }

    @Override
    public String deleteBillById(Long id) {
        hoaDonRepo.deleteBillByIdBill(id);
        return "success";
    }

    @Override
    public HoaDon addVoucherToBill(ProductVoucherUpdateRequest req) {
        HoaDon hoaDon = req.getHoaDon();
        hoaDon.setIdVoucher(req.getVoucher());
        return hoaDonRepo.save(hoaDon);
    }

    @Override
    public HoaDon updateHoaDon(HoaDon hoaDon) {
        return hoaDonRepo.save(hoaDon);
    }

    @Override
    public Voucher getVoucherByIdBill(Long id) {
        ArrayList<HoaDon> hoaDons = hoaDonRepo.getHoaDonByTrangThai(0);
        for (HoaDon hoaDon : hoaDons) {
            if (hoaDon.getId().equals(id)) {
                return hoaDon.getIdVoucher();
            }
        }
        return null;
    }

    @Override
    public KhachHang getCustomerByIdBill(Long id) {
        ArrayList<HoaDon> hoaDons = hoaDonRepo.getHoaDonByTrangThai(0);
        for (HoaDon hoaDon : hoaDons) {
            if (hoaDon.getId().equals(id)) {
                return hoaDon.getIdKhachHang();
            }
        }
        return null;
    }

    @Override
    public Page<HoaDon> getBillAndPanigation(Integer pageNo, Integer pageSize, Integer state) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        if (state == -1) {
            return hoaDonRepo.getAllBillPanigation(pageable);
        }
        return hoaDonRepo.getBillPanigationByState(pageable, state);
    }

    @Override
    public Page<HoaDon> getBillAndPanigationByIdCustomer(Integer pageNo, Integer pageSize, Integer state, Long id) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        if (state == -1) {
            return hoaDonRepo.getAllBillPanigationAllByIdCustomer(pageable, id);
        }
        return hoaDonRepo.getBillPanigationByStateByIdCustomer(pageable, state, id);
    }

    @Override
    public HoaDon getBillById(Long id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        if (hoaDon == null) {
            throw new RuntimeException("Không tìm tấy hóa đơn này.");
        }
        return hoaDon;
    }

    @Override
    public HoaDon getBillByCode(String code) {
        HoaDon hoaDon = hoaDonRepo.getHoaDonByMa(code);
        if (hoaDon == null) {
            throw new RuntimeException("Không tìm thấy hóa đơn hàng.");
        }
        return hoaDon;
    }

    @Override
    public RevenueResponce getRevenueMonth() {
        return hoaDonRepo.getRevenueMonth();
    }

    @Override
    public RevenueResponce getRevenueDay() {
        return hoaDonRepo.getRevenueDay();
    }

    @Override
    public Integer getQuantityOfProductWithMonth() {
        return hoaDonRepo.getQuantityOfProductWithMonth();
    }

    @Override
    public ArrayList<RevenueRangeDateResponce> getRevenueRangeDate(Date startDate, Date endDate) {
        return hoaDonRepo.getRevenueRangeDate(startDate, endDate);
    }

    @Override
    public ArrayList<ProductBestSellerResponce> getBestSeller() {
        return hoaDonRepo.getTop5ProductBestSeller();
    }

    @Override
    public ArrayList<BillStateResponce> getBillState() {
        return hoaDonRepo.getBillState();
    }

    @Override
    public ArrayList<ProductBestSellerResponce> getTop5ProductBestSellerFillter(Integer state) {
        try {
            if (state == 0) {
                return hoaDonRepo.getTop5ProductBestSellerDay();
            } else if (state == 1) {
                return hoaDonRepo.getTop5ProductBestSellerMonth();
            } else if (state == 2) {
                return hoaDonRepo.getTop5ProductBestSellerYear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<RevenueFillterResponce> getRevenueFillter(Integer state) {
        try {
            if (state == 0) {
                return hoaDonRepo.getRevenueRangeDay();
            } else if (state == 1) {
                return hoaDonRepo.getRevenueRangeMonth();
            } else if (state == 2) {
                return hoaDonRepo.getRevenueRangeYear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<BillRevenueResponse> getQuantityBillByState() {
        return hoaDonRepo.getQuantityBillByStates();
    }

    @Override
    public ArrayList<BillRevenueResponse> getQuantityBillWithState(Integer state) {
        return hoaDonRepo.getQuantityBillByBuyPaymentState(state);
    }

    @Override
    public ArrayList<BillRevenueResponse> getQuantityBillByStateAndIdCustomer(Long id) {
        return hoaDonRepo.getQuantityBillByStatesAndIDCustomer(id);
    }

    @Override
    public HoaDon getNewBill() {
        HoaDon bill = new HoaDon();
        bill.setMa(generateCode());
        return bill;
    }

}
