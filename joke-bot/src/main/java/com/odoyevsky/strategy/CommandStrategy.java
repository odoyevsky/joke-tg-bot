package com.odoyevsky.strategy;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface CommandStrategy {
    List<BotApiMethod<?>> handle(Update update);
}
