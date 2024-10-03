package com.example.demo.srt.sd77.entity.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeAddRequest {

    Long id;

    String cccd;

    String ma;

    String ten;

    Date ngaySinh;

    Boolean gioiTinh;

    String soDienThoai;

    String matKhau;

    String email;

    String avatar;

    Boolean trangThai;

    Integer chucVu;

    String xa;

    String phuong;

    String tinh;

    String diaChi;

    String maXa;

    String maPhuong;

    String maTinh;
}
