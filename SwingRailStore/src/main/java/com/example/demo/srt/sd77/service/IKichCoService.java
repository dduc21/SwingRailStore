package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.KichCo;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public interface IKichCoService {
    Page<KichCo> getSizes(int pageNo, int pageSize, String key);

    ArrayList<KichCo> getAllSize();

    KichCo getSizeById(Long id);

    KichCo addSize(Integer req);

    KichCo updateSize(KichCo req);
}
