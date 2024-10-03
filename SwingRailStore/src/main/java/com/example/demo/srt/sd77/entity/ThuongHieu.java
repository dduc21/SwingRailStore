package com.example.demo.srt.sd77.entity;

import com.example.demo.srt.sd77.entity.base.PrimaryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "thuong_hieu")
public class ThuongHieu extends PrimaryEntity {

    @Column(name = "ten")
    private String ten;

}
