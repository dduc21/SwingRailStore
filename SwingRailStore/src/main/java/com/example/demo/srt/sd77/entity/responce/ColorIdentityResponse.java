package com.example.demo.srt.sd77.entity.responce;

import com.example.demo.srt.sd77.entity.MauSac;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = MauSac.class)
public interface ColorIdentityResponse {

    @Value("#{target.id}")
    Long getId();

    @Value("#{target.ten}")
    String getTen();

}
