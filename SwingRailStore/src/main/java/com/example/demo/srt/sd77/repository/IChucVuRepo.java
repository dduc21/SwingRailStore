package com.example.demo.srt.sd77.repository;

import com.example.demo.srt.sd77.entity.ChucVu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IChucVuRepo extends JpaRepository<ChucVu, Long> {

    ChucVu findByMa(String ma);
}
