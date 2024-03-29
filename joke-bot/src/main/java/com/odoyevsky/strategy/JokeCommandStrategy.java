package com.odoyevsky.strategy;

import com.odoyevsky.exception.JokeNotFoundException;
import com.odoyevsky.exception.UserNotFoundException;
import com.odoyevsky.service.JokeService;
import com.odoyevsky.emojis.Emojis;
import com.odoyevsky.service.UserService;
import com.odoyevsky.utility.TgApiUtility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class JokeCommandStrategy implements CommandStrategy {
    private JokeService jokeService;
    private UserService userService;
    private TgApiUtility tgApiUtility;

    @Override
    public List<BotApiMethod<?>> handle(Update update) {
        List<BotApiMethod<?>> messages = new ArrayList<>();
        long chatId = update.getMessage().getChatId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        try {
            String messageText = jokeService.getRandomJoke().getText();
            sendMessage.setText(messageText);

            if (userService.isFavouriteJoke(chatId, messageText)) {
                sendMessage.setReplyMarkup(tgApiUtility.createInlineKeyboardMarkupBrokenHeart());
            } else sendMessage.setReplyMarkup(tgApiUtility.createInlineKeyboardMarkupHeart());

        } catch (JokeNotFoundException e) {
            sendMessage.setText("Я не знаю шуток на эту тему " + Emojis.CRYING);
            log.info(e.getMessage());
        } catch (UserNotFoundException e){
            sendMessage.setText("Что-то пошло не так. Попробуйте /start" + Emojis.CRYING);
            log.info(e.getMessage() + " " + chatId);
        }

        messages.add(sendMessage);
        return messages;
    }
}
