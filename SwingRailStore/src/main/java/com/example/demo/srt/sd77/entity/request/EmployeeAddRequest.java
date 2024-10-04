package com.example.demo.srt.sd77.entity.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeAddRequest {

    private Long id;

    private String cccd;

    private String ma;

    private String ten;

    private Date ngaySinh;

    private Boolean gioiTinh;

    private String soDienThoai;

    private String matKhau;

    private String email;

    private String avatar;

    private Boolean trangThai;

    private Integer chucVu;

    private String xa;

    private String phuong;

    private String tinh;

    private String diaChi;

    private String maXa;

    private String maPhuong;

    private String maTinh;
}
