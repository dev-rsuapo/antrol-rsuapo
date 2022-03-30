/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rsuapo.antrol.api.controllers;

/**
 *
 * @author RSU Aisyiyah Ponorogo <rsuapo@yahoo.co.id>
 */
import com.rsuapo.antrol.api.messages.AntreanMessage;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @SendTo("/topic/messages/{channel}")
    public AntreanMessage sendMessage(AntreanMessage message) {
        return message;
    }

}
