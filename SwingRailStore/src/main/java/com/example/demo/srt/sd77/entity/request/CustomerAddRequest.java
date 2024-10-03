package com.example.demo.srt.sd77.entity.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class CustomerAddRequest {

    private String ten;

    private Date ngaySinh;

    private Boolean gioiTinh;

    private String soDienThoai;

    private String cccd;

    private String email;

    private String avatar;

    private String xa;

    private String phuong;

    private String tinh;

    private String diaChi;

    private String maXa;

    private String maPhuong;

    private String maTinh;
}
