package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.Voucher;
import com.example.demo.srt.sd77.entity.request.VoucherAddRequest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public interface IVoucherService {
    Page<Voucher> getVouchers(int pageNo, int pageSize, String key, String trangThai);

    ArrayList<Voucher> getAllVoucher(String key);

    ArrayList<Voucher> getAllVoucherModal();

    Voucher getVoucherById(Long id);

    Voucher addVoucher(VoucherAddRequest req);

    Voucher updateVoucher(VoucherAddRequest req);

    String changeState(Long id);

    String generateCode();

    Voucher editVoucher(Voucher voucher);

    Voucher getVouchersByKeyAbsolute(String codeVoucher);
}
