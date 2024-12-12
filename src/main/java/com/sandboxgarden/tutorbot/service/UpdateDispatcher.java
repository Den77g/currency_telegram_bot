package com.sandboxgarden.tutorbot.service;

import com.sandboxgarden.tutorbot.service.handler.CallbackQueryHandler;
import com.sandboxgarden.tutorbot.service.handler.CommandHandler;
import com.sandboxgarden.tutorbot.service.handler.MessageHandler;
import com.sandboxgarden.tutorbot.telegram.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UpdateDispatcher {
    final MessageHandler messageHandler;
    final CommandHandler commandHandler;
    final CallbackQueryHandler callBackQueryHandler;

    @Autowired
    public UpdateDispatcher(MessageHandler messageHandler,
                            CommandHandler commandHandler,
                            CallbackQueryHandler callBackQueryHandler) {
        this.messageHandler = messageHandler;
        this.commandHandler = commandHandler;
        this.callBackQueryHandler = callBackQueryHandler;
    }

    public BotApiMethod<?> distribute(Update update, Bot bot){
        if (update.hasCallbackQuery()){
            return callBackQueryHandler.answer(update.getCallbackQuery(), bot);
        }
        if (update.hasMessage()){
            Message message = update.getMessage();

            if (message.hasText()){
                if (message.isCommand()){
                    return commandHandler.answer(message, bot);
                }
            }

            return messageHandler.answer(message, bot);
        }
       // log.info("Unsupported update: {}", update);
        return null;
    }

}
