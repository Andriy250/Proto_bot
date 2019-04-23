package controller.commands;

import Model.StatsRepository;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatsCommand extends BotCommand {

    private StatsRepository statsRepository;

    public StatsCommand(StatsRepository statsRepository) {
        super("petuh", "Find petuh among us");
        this.statsRepository = statsRepository;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage sendMessage = new SendMessage();

        Random random = new Random();
        List<String> keys = new ArrayList<String>(statsRepository.getUsers().keySet());
        String selectedUser = keys.get(random.nextInt(keys.size()));

        statsRepository.increment(selectedUser);

        sendMessage.setChatId(chat.getId().toString());
        sendMessage.setText(selectedUser + ", будеш петухом\n\n" + statsRepository);

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
