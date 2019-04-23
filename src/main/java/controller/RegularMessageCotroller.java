package controller;

import Model.IdRepository;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class RegularMessageCotroller {

    private Bot bot;
    private IdRepository repo;

    public RegularMessageCotroller() {}

    public RegularMessageCotroller(Bot bot, IdRepository repo) {
        this.bot = bot;
        this.repo = repo;
    }

    public void start() {
        LocalDate currDate = LocalDate.now();
        ///LocalTime currTime = LocalTime.now();

        Thread sendMessThread = new Thread(()->{
            Random random = new Random();
            int minutes = random.nextInt(60);
            int hour = random.nextInt(24);
            LocalTime time = LocalTime.of(hour, minutes);
            System.out.println(LocalTime.now());
            while (true){

                if (currDate.getDayOfMonth() != LocalDate.now().getDayOfMonth()) {
                    //if (currTime.getHour() != LocalTime.now().getHour()) {
                    minutes = random.nextInt(60);
                    hour = random.nextInt(24);
                    time = LocalTime.of(hour, minutes);
                }
                System.out.println(time);
                if ((time.getHour() == LocalTime.now().getHour()) &&(time.getMinute() == LocalTime.now().getMinute())) {
                    SendMessage mess = new SendMessage();

                    for (Long id : repo.getIdset()) {
                        mess.setChatId(id);
                        mess.setText("Такі шо? Хохли, давайте гроши");
                        synchronized (this) {
                            try{
                                bot.execute(mess);
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

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public IdRepository getRepo() {
        return repo;
    }

    public void setRepo(IdRepository repo) {
        this.repo = repo;
    }
}
