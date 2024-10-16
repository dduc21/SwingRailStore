package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.LichSu;
import com.example.demo.srt.sd77.entity.request.HistoryRequest;
import com.example.demo.srt.sd77.repository.iLichSuRepository;
import com.example.demo.srt.sd77.service.ILichSuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LichSuServiceImpl implements ILichSuService {

    @Autowired
    private iLichSuRepository lichSuRepo;

    @Override
    public ArrayList<LichSu> getAllLichSu(Long id) {
        return lichSuRepo.findAllByIdHoaDon(id);
    }

    @Override
    public LichSu addHistory(HistoryRequest lichSu) {
        LichSu history = new LichSu();
        history.setTrangThai(lichSu.getTrangThai());
        history.setGhiChu(lichSu.getGhiChu());
        history.setIdHoaDon(lichSu.getHoaDon());
        return lichSuRepo.save(history);
    }

}
