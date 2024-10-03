package com.example.demo.srt.sd77.entity;

import com.example.demo.srt.sd77.entity.base.PrimaryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "kich_co")
public class KichCo extends PrimaryEntity {

    @Column(name = "kich_co")
    private Integer kichCo;

}
