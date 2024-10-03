package com.example.demo.srt.sd77.controller;

import com.example.demo.srt.sd77.infrastructure.configs.VNPay.VNPayService;
import com.example.demo.srt.sd77.service.impl.VnPayServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class VnPayRestController {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/payment")
    public String payment(@RequestParam("total")Integer total,
                          @RequestParam("orderInfor")String orderInfor,
                          @RequestParam("currentLocation")String currentLocation,
                          HttpSession session) {
        try{
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            return vnPayService.createOrder(total, orderInfor, baseUrl);
        }catch (Exception ex){
            return ex.getMessage();
        }
    }

}
