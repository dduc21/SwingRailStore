package com.example.demo.srt.sd77.controller.web_socket;

import com.example.demo.srt.sd77.infrastructure.configs.web_socket.Hello;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;   

@Controller
public class WebSocketController {

    @MessageMapping("/bills")
    @SendTo("/bill/bills")
    public String getListBill(@Payload Hello message) {
        System.out.println(message);
        return message.getName() ;
    }

    @MessageMapping("/bill-detail")
    @SendTo("/bill/bill-detail")
    public String changeNotificationBillDetail(@Payload Hello message) {
        System.out.println(message);
        return message.getName() ;
    }
}
