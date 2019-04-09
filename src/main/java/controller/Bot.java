package controller;

import Model.IdRepository;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
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

        if (matcher.find()) {
            sendMessage.setText("привіт, @" + message.getFrom().getUserName());
            if (message.getFrom().getUserName().equals("l_l_e_Tu"))
                sendMessage.setText("Здраствуй, батьку");
            if (message.getFrom().getUserName().equals("olevolo"))
                sendMessage.setText("прив... а це ти вова?");
            if (message.getFrom().getUserName().equals("null"))
                sendMessage.setText("лол, зроби собі нарешті нік, користовуч @null");
            sendMessage.setChatId(message.getChatId());
            try{
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendRegMessMessage(){
        //LocalDate currDate = LocalDate.now();
        LocalTime currTime = LocalTime.now();

        Thread sendMessThread = new Thread(()->{
            Random random = new Random();
            int minutes = random.nextInt(60);
            LocalTime time = LocalTime.of(LocalTime.now().getHour(), minutes);
            System.out.println(LocalTime.now());
            while (true){

                //if (currDate.getDayOfMonth() != LocalDate.now().getDayOfMonth())
                if (currTime.getHour() != LocalTime.now().getHour()) {
                    minutes = random.nextInt(60);
                    time = LocalTime.of(LocalTime.now().getHour(), minutes);
                }
                System.out.println(time);
                if ((time.getHour() == LocalTime.now().getHour()) &&(time.getMinute() == LocalTime.now().getMinute())) {
                    SendMessage mess = new SendMessage();

                    for (Long id : repo.getIdset()) {
                        mess.setChatId(id);
                        mess.setText("Не очікував? Я вас ще здивую. І до речі. ПОРОХ ПРЕЗІДЄНТ МІРА!!!УРА!!!!");
                        synchronized (this) {
                            try{
                                execute(mess);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e ) {}
            }
        });
        sendMessThread.setDaemon(true);
        sendMessThread.start();
    }

    public String getBotToken() {
        return token;
    }
}
