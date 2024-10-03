package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.KhachHang;
import com.example.demo.srt.sd77.entity.request.CustomerAddRequest;
import com.example.demo.srt.sd77.entity.request.CustomerRegisterRequest;
import com.example.demo.srt.sd77.repository.IKhachHangRepo;
import com.example.demo.srt.sd77.service.IKhachHangSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class KhachHangSerImpl implements IKhachHangSer {

    @Autowired
    private IKhachHangRepo khachHangRepo;

    public Page<KhachHang> getCustomersWithPanigation(int pageNo, int pageSize, String key, String trangThai){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return khachHangRepo.findCustomers(pageable,
                "%" + trangThai + "%",
                "%" + key + "%");
    }

    public ArrayList<KhachHang> getAllCustomers(){
//        get all voucher
        return (ArrayList<KhachHang>) khachHangRepo.findAll();
    }

    public KhachHang getCustomerById(Long id){
        return khachHangRepo.findKhachHangById(id).get(0);
    }

    public KhachHang addCustomer(CustomerAddRequest req){
        // add customer
        if(khachHangRepo.findKhachHangByEmail(req.getEmail()) != null){
            throw new RuntimeException("Email đã tồn tại");
        }else {
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

            return khachHangRepo.save(khachHang);
        }
    }


    public String generateCode(){
        // generate code
        String newestCode = khachHangRepo.generateNewestCode();
        if(newestCode == null || newestCode.equals("")){
            return "CUSTOMER_" + 0;
        }
        return "CUSTOMER_" + (Integer.parseInt(newestCode.substring(9)) + 1);
    }

    public KhachHang updateCustomer(KhachHang khachHang){
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

        return khachHangRepo.save(customer);
    }

    public KhachHang register(CustomerRegisterRequest req){
        if(khachHangRepo.findKhachHangByEmail(req.getEmail()) != null){
            throw new RuntimeException("Email đã tồn tại");
        }else {
            KhachHang customer = new KhachHang();
            customer.setTen(req.getTen());
            customer.setEmail(req.getEmail());
            customer.setMatKhau(req.getMatKhau());
            customer.setTrangThai(true);
            return khachHangRepo.save(customer);
        }
    }

    public KhachHang login(String email, String matKhau){
        KhachHang kh =  khachHangRepo.findKhachHangByEmailAndPass(email, matKhau);

        if(kh == null){
            throw new RuntimeException("Tài khoản hoặc mật khẩu không đúng");
        }
        return kh;
    }

    public void changePass(String matKhau, String id){
        khachHangRepo.changePass(matKhau, id);
    }

}
