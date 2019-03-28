import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import controller.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    static public void main(String ... args){

        ApiContextInitializer.init();

            TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            Bot myBot = new Bot();
            botsApi.registerBot(myBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
