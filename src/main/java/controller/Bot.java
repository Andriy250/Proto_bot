package controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bot extends TelegramLongPollingBot {

    private final String username = "Methuselah";
    private final String token = "790292244:AAEoL22wZZ1AHn0XckumxtAnNoADp_XTEJQ";


    public void onUpdateReceived(Update update)  {
        String message = update.getMessage().getText();
        SendMessage sendMessage = new SendMessage();

        if (message.equals("weather")) {
            URL url = null;
            String weatherJSON = null;
            try {
                url = new URL("https://api.openweathermap.org/data/2.5/weather?q=lviv&APPID=4bb9969e1d07e6dd69a8824e9f15f358&units=metric");


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                weatherJSON = bufferedReader.readLine();

                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sendMessage.setText(weatherJSON);
            sendMessage.setChatId(update.getMessage().getChatId());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

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

    public String getBotUsername() {
        return username;
    }

    public String getBotToken() {
        return token;
    }
}
