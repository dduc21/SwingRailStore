package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.NhanVien;
import com.example.demo.srt.sd77.entity.request.EmployeeAddRequest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public interface INhanVienService {
    Page<NhanVien> getEmployeesWithPanigation(int pageNo, int pageSize, String key, String trangThai);

    ArrayList<NhanVien> getAllCustomers();

    NhanVien getEmployeeByID(Long id);

    NhanVien addEmployee(EmployeeAddRequest req);

    String generateCode();

    NhanVien updateEmployee(EmployeeAddRequest req);

    NhanVien login(String email, String matKhau);

    String changePass(String oldPass, String newPass, Long id);
}
