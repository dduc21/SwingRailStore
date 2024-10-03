package com.example.demo.srt.sd77.infrastructure.listener;

import com.example.demo.srt.sd77.entity.base.PrimaryEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.Date;

public class PrimaryEntityListener {

    @PrePersist
    private void onCreate(PrimaryEntity entity) {
        entity.setNgayTao(new Date());
        entity.setNgayCapNhat(new Date());
        entity.setDeleted(true);
    }

    @PreUpdate
    private void onUpdate(PrimaryEntity entity) {
        entity.setNgayCapNhat(new Date());
    }
}
