package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.LichSu;
import com.example.demo.srt.sd77.entity.request.HistoryRequest;
import com.example.demo.srt.sd77.repository.iLichSuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LichSuServiceImpl {

    @Autowired
    private iLichSuRepository lichSuRepo;

    public ArrayList<LichSu> getAllLichSu(Long id) {
        return lichSuRepo.findAllByIdHoaDon(id);
    }

    public LichSu addHistory(HistoryRequest lichSu) {
        LichSu history = new LichSu();

//        update history
        history.setTrangThai(lichSu.getTrangThai());
        history.setGhiChu(lichSu.getGhiChu());
        history.setIdHoaDon(lichSu.getHoaDon());
        return lichSuRepo.save(history);
    }

}
