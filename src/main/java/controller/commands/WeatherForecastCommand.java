package controller.commands;


import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecastCommand extends BotCommand {

    public WeatherForecastCommand() {
        super("weather", "Get weather forecast");
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

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

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId().toString());
        sendMessage.setText(weatherJSON);
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
