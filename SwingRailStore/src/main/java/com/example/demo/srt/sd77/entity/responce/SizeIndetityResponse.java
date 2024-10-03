package com.example.demo.srt.sd77.entity.responce;

import com.example.demo.srt.sd77.entity.KichCo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = KichCo.class)
public interface SizeIndetityResponse {

    @Value("#{target.id}")
    Long getId();

    @Value("#{target.kich_co}")
    Long getKichCo();
}
