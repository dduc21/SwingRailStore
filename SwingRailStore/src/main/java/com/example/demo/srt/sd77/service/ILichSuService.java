package com.example.demo.srt.sd77.service;

import com.example.demo.srt.sd77.entity.LichSu;
import com.example.demo.srt.sd77.entity.request.HistoryRequest;

import java.util.ArrayList;

public interface ILichSuService {
    ArrayList<LichSu> getAllLichSu(Long id);

    LichSu addHistory(HistoryRequest lichSu);
}
