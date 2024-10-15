package com.example.demo.srt.sd77.service.impl;

import com.example.demo.srt.sd77.entity.KichCo;
import com.example.demo.srt.sd77.repository.IKichCoRepository;
import com.example.demo.srt.sd77.service.IKichCoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class KichCoServiceImpl implements IKichCoService {

    @Autowired
    private IKichCoRepository kichCoRepo;


    public Page<KichCo> getSizes(int pageNo, int pageSize, String key){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return kichCoRepo.findSizePanigation(pageable,
                "%" + key + "%");
    }

    public ArrayList<KichCo> getAllSize(){
//        get all voucher
        return (ArrayList<KichCo>) kichCoRepo.findAll();
    }

    public KichCo getSizeById(Long id){
        return kichCoRepo.findSizeById(id).get(0);
    }

    public KichCo addSize(Integer req){
        KichCo size = new KichCo();

        if(!kichCoRepo.findSizeBySize(req).isEmpty()){
            throw new RuntimeException("The size is already exists");
        }
        size.setKichCo(req);

        return kichCoRepo.save(size);
    }

    public KichCo updateSize(KichCo req){
        ArrayList<KichCo> sizes = kichCoRepo.findSizeBySize(req.getKichCo());

        if(!sizes.isEmpty()){
            if(!sizes.get(0).getId().equals(req.getId())){
                if(sizes.get(0).getKichCo().equals(req.getKichCo())){
                    throw new RuntimeException("The size is already exists");
                }
            }
        }

        return kichCoRepo.save(req);
    }
}
