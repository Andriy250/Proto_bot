package controller;

import Model.IdRepository;
import controller.commands.WeatherForecastCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bot extends TelegramLongPollingCommandBot {

    private IdRepository repo;
    static private final String botname = "Methuselah";
    private final String token = "790292244:AAEoL22wZZ1AHn0XckumxtAnNoADp_XTEJQ";

    public Bot() {
        super(botname);

        register(new WeatherForecastCommand());


        //repo = new IdRepository("src/main/resources/ChatIds.txt");
        repo = new IdRepository("ChatIds.txt");


        try {
            repo.fullfillFromFile();
        } catch (IOException e) { e.printStackTrace(); }


        sendRegMessMessage();
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        try {
            repo.saveIdToFile(message.getChatId());
        } catch (IOException e) { e.printStackTrace(); }

        Pattern pattern = Pattern.compile("([H|h]i|[П|п]ривіт)");
        Matcher matcher = pattern.matcher(update.getMessage().getText());
        System.out.println(message.getText() + " " + message.getFrom().getUserName());
        if (matcher.find()) {
            sendMessage.setText("привіт, @" + message.getFrom().getUserName());
            if (message.getFrom().getUserName() == null)
                sendMessage.setText("лол, зроби собі нарешті нік, користовуч @null");
            else {
                if (message.getFrom().getUserName().equals("l_l_e_Tu"))
                    sendMessage.setText("Здраствуй, батьку");
                if (message.getFrom().getUserName().equals("olevolo"))
                    sendMessage.setText("прив... а це ти вова?");
            }
            sendMessage.setChatId(message.getChatId());
            try{
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }




    public void sendRegMessMessage(){
        RegularMessageCotroller regularMessageCotroller = new RegularMessageCotroller(this, repo);

        regularMessageCotroller.start();
    }

    public String getBotToken() {
        return token;
    }
}
