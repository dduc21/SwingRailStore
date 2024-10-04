package com.example.demo.srt.sd77.entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDetailSearchRequest {

    private String ids;

    private Integer pageNo;

    private Integer pageSize;

    private String key;

    private Long idMauSac;

    private Long idTheLoai;

    private Long idKichCo;
}
