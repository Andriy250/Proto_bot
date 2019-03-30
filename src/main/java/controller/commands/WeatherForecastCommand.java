package controller.commands;


import org.json.JSONArray;
import org.json.JSONObject;
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
import Model.Weather;

public class WeatherForecastCommand extends BotCommand {

    public WeatherForecastCommand() {
        super("weather", "Get weather forecast");
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        URL url = null;
        String weatherJSON = null;
        SendMessage sendMessage = new SendMessage();
        try {

            url = (strings.length == 0) ?
                    new URL("https://api.openweathermap.org/data/2.5/weather?q=lviv&APPID=4bb9969e1d07e6dd69a8824e9f15f358&units=metric") :
                    new URL("https://api.openweathermap.org/data/2.5/weather?q="+strings[0] +"&APPID=4bb9969e1d07e6dd69a8824e9f15f358&units=metric");


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            weatherJSON = bufferedReader.readLine();
            bufferedReader.close();

            Weather weather = new Weather();
            JSONObject jsonObject = new JSONObject(weatherJSON);
            weather.setName(jsonObject.getString("name"));


            JSONObject innerJO = jsonObject.getJSONObject("main");
            weather.setTemp(innerJO.getDouble("temp"));
            weather.setHumidity(innerJO.getDouble("humidity"));

            JSONArray getArray = jsonObject.getJSONArray("weather");
            for (int i = 0 ; i < getArray.length(); ++i){
                JSONObject obj = getArray.getJSONObject(i);
                weather.setIcon((String)obj.get("icon"));
                weather.setMain((String)obj.get("main"));
            }

            sendMessage.setChatId(chat.getId().toString());
            sendMessage.setText(weather.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            sendMessage.setChatId(chat.getId().toString());
            sendMessage.setText("Вибач, не знаю такого міста.");
        } finally {
            try {
                absSender.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    public String getWeather(String message, Weather weather){
        return "";
    }
}
