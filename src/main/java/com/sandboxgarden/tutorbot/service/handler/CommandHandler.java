package com.sandboxgarden.tutorbot.service.handler;

import com.sandboxgarden.tutorbot.telegram.Bot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class CommandHandler {

    public BotApiMethod<?> answer(Message message, Bot bot){
        String command = message.getText();

        switch (command){
            case "/start" -> {
                return start(message);
            }
        }

        return null;
    }

    private BotApiMethod<?> start(Message message){
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("""
                        "Hello I'm Bot, that makes simple talk between tutor and puple"
                        
                        What can I do?
                        - Prepare the schedule
                        - Pit home task
                        - Make control of success

                        """)
                .build();
    }

}
