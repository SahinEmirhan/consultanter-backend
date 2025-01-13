package com.example.demo.controller;

import com.example.demo.DTO.CaseIdMessageDTO;
import com.example.demo.model.Message;
import com.example.demo.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ComplaintMessagesWebSocketController {

    @Autowired
    private ComplaintService complaintService;

    @MessageMapping("/complaint/{id}")
    @SendTo("/topic/complaint/{id}")
    public String sendAMessage(@Payload String text, @DestinationVariable("id") Long id, SimpMessageHeaderAccessor headerAccessor) {

        Message sentMessage = complaintService.sendMessage(text, id, (String) headerAccessor.getSessionAttributes().get("userType"));

        String msg =sentMessage.toString();
        return msg;
    }

}
