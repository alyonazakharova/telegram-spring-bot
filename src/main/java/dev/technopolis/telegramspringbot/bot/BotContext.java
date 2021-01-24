package dev.technopolis.telegramspringbot.bot;

import dev.technopolis.telegramspringbot.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BotContext {
    private final ChatBot bot;
    private final User user;
    private final String input;

    public static BotContext of(ChatBot bot, User user, String text) {
        return new BotContext(bot, user, text);
    }
}
