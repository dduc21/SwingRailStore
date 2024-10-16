package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.KhachHang;
import com.example.demo.srt.sd77.entity.request.CustomerAddRequest;
import com.example.demo.srt.sd77.entity.request.CustomerRegisterRequest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public interface IKhachHangService {
    Page<KhachHang> getCustomersWithPanigation(int pageNo, int pageSize, String key, String trangThai);
    List<KhachHang> rankingCustomer();
    ArrayList<KhachHang> getAllCustomers();
    KhachHang getCustomerById(Long id);
    KhachHang addCustomer(CustomerAddRequest req);
    String generateCode();
    KhachHang updateCustomer(KhachHang khachHang);
    KhachHang register(CustomerRegisterRequest req);
    KhachHang login(String email, String matKhau);
    KhachHang updatePoints(Long id, int points);
}
