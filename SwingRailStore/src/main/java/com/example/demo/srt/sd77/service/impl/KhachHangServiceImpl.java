package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.KhachHang;
import com.example.demo.srt.sd77.entity.request.CustomerAddRequest;
import com.example.demo.srt.sd77.entity.request.CustomerRegisterRequest;
import com.example.demo.srt.sd77.repository.IKhachHangRepository;
import com.example.demo.srt.sd77.service.IKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KhachHangServiceImpl implements IKhachHangService {

    @Autowired
    private IKhachHangRepository khachHangRepo;

    @Override
    public Page<KhachHang> getCustomersWithPanigation(int pageNo, int pageSize, String key, String trangThai) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return khachHangRepo.findCustomers(pageable,
                "%" + trangThai + "%",
                "%" + key + "%");
    }

    @Override
    public List<KhachHang> rankingCustomer() {

        return khachHangRepo.rankingCustomers();
    }

    @Override
    public ArrayList<KhachHang> getAllCustomers() {
        return (ArrayList<KhachHang>) khachHangRepo.findAll();
    }

    @Override
    public KhachHang getCustomerById(Long id) {
        return khachHangRepo.findKhachHangById(id).get(0);
    }

    @Override
    public KhachHang addCustomer(CustomerAddRequest req) {
        // add customer
        if (khachHangRepo.findKhachHangByEmail(req.getEmail()) != null) {
            throw new RuntimeException("Email đã tồn tại");
        } else {
            KhachHang khachHang = new KhachHang();

            khachHang.setMa(generateCode());
            khachHang.setTen(req.getTen());
            khachHang.setNgaySinh(req.getNgaySinh());
            khachHang.setGioiTinh(req.getGioiTinh());
            khachHang.setSoDienThoai(req.getSoDienThoai());
            khachHang.setCccd(req.getCccd());
            khachHang.setEmail(req.getEmail());
            khachHang.setAvatar(req.getAvatar());
            khachHang.setXa(req.getXa());
            khachHang.setPhuong(req.getPhuong());
            khachHang.setTinh(req.getTinh());
            khachHang.setDiaChi(req.getDiaChi());
            khachHang.setMaXa(req.getMaXa());
            khachHang.setMaPhuong(req.getMaPhuong());
            khachHang.setMaTinh(req.getMaTinh());
            khachHang.setTrangThai(true);
            khachHang.setMatKhau("123");
            khachHang.setTichDiem(0);
            return khachHangRepo.save(khachHang);
        }
    }

    @Override
    public String generateCode() {
        String newestCode = khachHangRepo.generateNewestCode();
        if (newestCode == null || newestCode.equals("")) {
            return "CUSTOMER_" + 0;
        }
        return "CUSTOMER_" + (Integer.parseInt(newestCode.substring(9)) + 1);
    }

    @Override
    public KhachHang updateCustomer(KhachHang khachHang) {
        KhachHang customer = new KhachHang();
        customer.setCccd(khachHang.getCccd());
        customer.setDiaChi(khachHang.getDiaChi());
        customer.setEmail(khachHang.getEmail());
        customer.setGioiTinh(khachHang.getGioiTinh());
        customer.setMa(khachHang.getMa());
        customer.setMaPhuong(khachHang.getMaPhuong());
        customer.setMaTinh(khachHang.getMaTinh());
        customer.setMaXa(khachHang.getMaXa());
        customer.setNgaySinh(khachHang.getNgaySinh());
        customer.setPhuong(khachHang.getPhuong());
        customer.setSoDienThoai(khachHang.getSoDienThoai());
        customer.setTen(khachHang.getTen());
        customer.setTinh(khachHang.getTinh());
        customer.setAvatar(khachHang.getAvatar());
        customer.setId(khachHang.getId());
        customer.setXa(khachHang.getXa());
        customer.setTrangThai(khachHang.getTrangThai());
        customer.setNgayTao(khachHang.getNgayTao());
        customer.setMatKhau(khachHang.getMatKhau());
        customer.setTichDiem(khachHang.getTichDiem());
        return khachHangRepo.save(customer);
    }

    @Override
    public KhachHang register(CustomerRegisterRequest req) {
        if (khachHangRepo.findKhachHangByEmail(req.getEmail()) != null) {
            throw new RuntimeException("Email đã tồn tại");
        } else {
            KhachHang customer = new KhachHang();
            customer.setTen(req.getTen());
            customer.setEmail(req.getEmail());
            customer.setMatKhau(req.getMatKhau());
            customer.setTrangThai(true);
            return khachHangRepo.save(customer);
        }
    }

    @Override
    public KhachHang login(String email, String matKhau) {
        KhachHang kh = khachHangRepo.findKhachHangByEmailAndPass(email, matKhau);

        if (kh == null) {
            throw new RuntimeException("Tài khoản hoặc mật khẩu không đúng");
        }
        return kh;
    }

    @Override
    public KhachHang updatePoints(Long id, int points) {
        KhachHang customer = khachHangRepo.findById(id).orElse(null);
        if (customer != null) {
            customer.setTichDiem(customer.getTichDiem() + points);
            return khachHangRepo.save(customer);
        }
        return null;
    }

    public void changePass(String matKhau, String id) {
        khachHangRepo.changePass(matKhau, id);
    }

}
