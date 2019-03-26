package Contoller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bot extends TelegramLongPollingBot {

    private final String username = "Methuselah";
    private final String token = "790292244:AAEoL22wZZ1AHn0XckumxtAnNoADp_XTEJQ";


    public void onUpdateReceived(Update update) {
        /*System.out.println("Caption " + update.getMessage().getCaption() + "\n|Signature " + update.getMessage().getAuthorSignature() + "\n|Text " +
                update.getMessage().getText() + "\n|User " + update.getMessage().getFrom() + "\n|Contact " + update.getMessage().getContact());*/
        SendMessage sendMessage = new SendMessage();
        Pattern pattern = Pattern.compile("([H|h]i|[П|п]ривіт)");
        Matcher matcher = pattern.matcher(update.getMessage().getText());
        if (matcher.matches()) {
            sendMessage.setText("привіт, @" + update.getMessage().getFrom().getUserName());
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
