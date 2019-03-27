package controller.commands;


import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class WeatherForecastCommand extends BotCommand {

    public WeatherForecastCommand() {
        super("weather", "Get weather forecast");
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId().toString());
        sendMessage.setText("Що коїться");
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
