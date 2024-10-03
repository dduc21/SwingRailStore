package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.Voucher;
import com.example.demo.srt.sd77.entity.request.VoucherAddRequest;
import com.example.demo.srt.sd77.enums.StatusVoucher;
import com.example.demo.srt.sd77.repository.IVoucherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class VoucherSerImpl {

    @Autowired
    private IVoucherRepo voucherRepo;

    public Page<Voucher> getVouchers(int pageNo, int pageSize, String key, String trangThai){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return voucherRepo.findVouchersByState(pageable,
                                        "%" + trangThai + "%",
                                            "%" + key + "%");
    }

    public ArrayList<Voucher> getAllVoucher(String key){
//        get all voucher
        return (ArrayList<Voucher>) voucherRepo.getVouchersByKey("%" + key + "%");
    }

    public Voucher getVoucherById(Long id){
        return voucherRepo.findVoucherByIdAndState(id).get(0);
    }

    public Voucher addVoucher(VoucherAddRequest req){
        // check state
        Date today = new Date();

        // add voucher
        Voucher voucher = new Voucher();

        voucher.setMa(generateCode());
        voucher.setTen(req.getTen());
        voucher.setPhanTramGiam(req.getPhanTramGiam());
        voucher.setGiaTriToiThieu(req.getGiaTriToiThieu());
        voucher.setGiaTriToiDa(req.getGiaTriToiDa());
        voucher.setSoLanDung(req.getSoLanDung());
        voucher.setNgayBatDau(req.getNgayBatDau());
        voucher.setNgayKetThuc(req.getNgayKetThuc());
        voucher.setLoaiVoucher(req.getLoaiVoucher());

        if(req.getNgayBatDau().after(today)){
            voucher.setTrangThai(StatusVoucher.CHUA_BAT_DAU.getTrangThai());
        }
        if(req.getNgayKetThuc().before(today)){
            voucher.setTrangThai(StatusVoucher.KET_THUC.getTrangThai());
        }
        if(req.getNgayBatDau().before(today) && req.getNgayKetThuc().after(today)){
            voucher.setTrangThai(StatusVoucher.DANG_DIEN_RA.getTrangThai());
        }

        return voucherRepo.save(voucher);
    }

    public Voucher updateVoucher(VoucherAddRequest req){
        // add voucher
        Voucher voucher = new Voucher();

        voucher.setId(req.getId());
        voucher.setMa(req.getMa());
        voucher.setTen(req.getTen());
        voucher.setPhanTramGiam(req.getPhanTramGiam());
        voucher.setGiaTriToiThieu(req.getGiaTriToiThieu());
        voucher.setGiaTriToiDa(req.getGiaTriToiDa());
        voucher.setSoLanDung(req.getSoLanDung());
        voucher.setNgayBatDau(req.getNgayBatDau());
        voucher.setNgayKetThuc(req.getNgayKetThuc());
        voucher.setTrangThai(req.getTrangThai());
        voucher.setLoaiVoucher(req.getLoaiVoucher());

        return voucherRepo.save(voucher);
    }

    public String changeState(Long id){
        // check state
        Date today = new Date();
        Voucher voucher = voucherRepo.findById(id).isPresent() ? voucherRepo.findById(id).get() : null;

        if(voucher == null){
            return "Không tìm thấy voucher";
        }

        if(voucher.getTrangThai() == StatusVoucher.KET_THUC.getTrangThai()){
            return "Voucher đã kết thúc";
        }

        if(voucher.getTrangThai() == StatusVoucher.HUY.getTrangThai()){
            if(voucher.getNgayBatDau().after(today)){
                voucher.setTrangThai(StatusVoucher.CHUA_BAT_DAU.getTrangThai());
            }
            if(voucher.getNgayKetThuc().before(today)){
                voucher.setTrangThai(StatusVoucher.KET_THUC.getTrangThai());
            }
            if(voucher.getNgayBatDau().before(today) && voucher.getNgayKetThuc().after(today)){
                voucher.setTrangThai(StatusVoucher.DANG_DIEN_RA.getTrangThai());
            }
            voucherRepo.save(voucher);
            return "Voucher khôi phục thành công";
        }

        voucher.setTrangThai(StatusVoucher.HUY.getTrangThai());
        voucherRepo.save(voucher);
        return "Thay đổi trạng thái thành công";
    }

    public String generateCode(){
        // generate code
        String newestCode = voucherRepo.generateNewestCode();
        if(newestCode == null || newestCode.equals("")){
            return "VOUCHER_" + 0;
        }
        return "VOUCHER_" + (Integer.parseInt(newestCode.substring(8)) + 1);
    }

    public Voucher editVoucher(Voucher voucher){
        Date today = new Date();

        if(voucher.getNgayBatDau().after(today)){
            voucher.setTrangThai(StatusVoucher.CHUA_BAT_DAU.getTrangThai());
        }
        if(voucher.getNgayKetThuc().before(today)){
            voucher.setTrangThai(StatusVoucher.KET_THUC.getTrangThai());
        }
        if(voucher.getNgayBatDau().before(today) && voucher.getNgayKetThuc().after(today)){
            voucher.setTrangThai(StatusVoucher.DANG_DIEN_RA.getTrangThai());
        }
        return voucherRepo.save(voucher);
    }

    public Voucher getVouchersByKeyAbsolute(String codeVoucher){
        Voucher voucher = voucherRepo.getVouchersByKeyAbsolute(codeVoucher);
        if(voucher == null){
            throw new RuntimeException("Không tìm thấy voucher");
        }
        return voucher;
    }
}
