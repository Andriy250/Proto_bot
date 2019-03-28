package controller;

import controller.commands.WeatherForecastCommand;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bot extends TelegramLongPollingCommandBot {

    
    static private final String botname = "Methuselah";
    private final String token = "790292244:AAEoL22wZZ1AHn0XckumxtAnNoADp_XTEJQ";

    public Bot() {
        super(botname);

        register(new WeatherForecastCommand());
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();

        Pattern pattern = Pattern.compile("([H|h]i|[П|п]ривіт)");
        Matcher matcher = pattern.matcher(update.getMessage().getText());

        if (matcher.find()) {
            sendMessage.setText("привіт, @" + update.getMessage().getFrom().getUserName());
            if (update.getMessage().getFrom().getUserName().equals("l_l_e_Tu"))
                sendMessage.setText("Здраствуй, батьку");
            if (update.getMessage().getFrom().getUserName().equals("olevolo"))
                sendMessage.setText("прив... а це ти вова?");
            if (update.getMessage().getFrom().getUserName().equals("null"))
                sendMessage.setText("лол, зроби собі нарешті нік, користовуч @null");
            sendMessage.setChatId(update.getMessage().getChatId());
            try{
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBotToken() {
        return token;
    }
}
