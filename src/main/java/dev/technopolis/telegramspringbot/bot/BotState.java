package dev.technopolis.telegramspringbot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public enum BotState {
    Start {
        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Дратути!");
        }

        @Override
        public BotState nextState() {
            return EnterPhone;
        }
    },

    EnterPhone {
        private BotState next;

        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Укажите свой номер телефона:");
        }

        @Override
        public void handleInput(BotContext context) {
            String phone = context.getInput();

            if (Utils.isValidPhoneNumber(phone)) {
                context.getUser().setPhone(phone);
                next = EnterEmail;
            } else {
                sendMessage(context, "Пожалуйста, введите номер в формате 8ХХХХХХХХХХ");
                next = EnterPhone;
            }

        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    EnterEmail {
        private BotState next;

        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Укажите свой email:");
        }

        @Override
        public void handleInput(BotContext context) {
            String email = context.getInput();

            if (Utils.isValidEmailAddress(email)) {
                context.getUser().setEmail(context.getInput());
                next = Approved;
            } else {
                sendMessage(context, "Введён некорректный email. Пожалуйста, попробуйте ещё раз.");
                next = EnterEmail;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    Approved(false) {
        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Заявка успешно создана!");
        }

        @Override
        public BotState nextState() {
            return Start;
        }
    };

    private static BotState[] states;
    private final boolean inputNeeded;

    BotState() {
        this.inputNeeded = true;
    }

    BotState(boolean inputNeeded) {
        this.inputNeeded = inputNeeded;
    }

    public static BotState getInitialState() {
        return byId(0);
    }

    public static BotState byId(int id) {
        if (states == null) {
            states = BotState.values();
        }

        return states[id];
    }

    protected void sendMessage(BotContext context, String text) {
        SendMessage message = new SendMessage()
                .setChatId(context.getUser().getChatId())
                .setText(text);
        try {
            context.getBot().execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public boolean isInputNeeded() {
        return inputNeeded;
    }

    public void handleInput(BotContext context) {

    }

    public abstract void enter(BotContext context);
    public abstract BotState nextState();
}
