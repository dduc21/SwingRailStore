package com.example.demo.srt.sd77.entity.request;

import com.example.demo.srt.sd77.entity.HoaDon;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryRequest {

    private Integer trangThai;

    private HoaDon hoaDon;

    private String ghiChu;
}
