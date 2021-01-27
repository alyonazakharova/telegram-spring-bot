package dev.technopolis.telegramspringbot.bot;

import org.apache.commons.validator.EmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static boolean isValidEmailAddress(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public static boolean isValidPhoneNumber(String phone) {
        Pattern pattern = Pattern.compile("8\\d{10}");
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
