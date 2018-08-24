qweasdzxc
import org.telegram.telegrambots.*;
import org.telegram.telegrambots.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.*;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.Contact;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.Timer;
import java.util.concurrent.Executors;

public class Test extends TelegramLongPollingBot {
    public static Integer[] sheetId = {0, 126089624};

    public static void main(String[] args) throws TelegramApiException, InterruptedException {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Test());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

         Timer time = new Timer();
         ScheduledTask st = new ScheduledTask();
         time.scheduleAtFixedRate(st,st.start.getTime(), 86400000);
    }

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Boolean exists = true;
        Integer intSheetId = 0;
        Integer lineName = 0;
        String sheetName = null;
        StatesDAO statesDao = new StatesDAO();
        TableDAO tableDao = new TableDAO();
        Message message = update.getMessage();
        Integer userId = update.getMessage().getFrom().getId();
        GoogleWrite googleWrite = new GoogleWrite();
        if (message.getText().equals("Измени")) {
         //  try {
                tableDao.updateDbWithDifferences();
               // System.out.println(googleWrite.checkLastLine("Лист1"));
         //   }
           // catch (java.io.IOException e){
             //   e.printStackTrace();
       //     }

        } else {

            if (statesDao.checkIfExistsUser(userId)) {
                switch (statesDao.stateCheck(userId)) {
                    case ("AddName"): {
                        tableDao.addNewName(message.getText(), userId);

                        statesDao.stateUpdateAddTask(userId);
                        tableDao.closeConnection();
                        sendMsg(message, "Введите задачу");
                        break;
                    }
                    case ("AddTask"): {
                        tableDao.addNewTask(userId, message.getText());
                        statesDao.stateUpdateAddDate(userId);
                        tableDao.closeConnection();
                        sendMsg(message, "Введите дату выполнения, формат ДД.ММ.ГГГГ");
                        break;
                    }
                    case ("AddDate"): {
                        tableDao.addNewTaskDate(userId, message.getText());
                        statesDao.stateUpdateAddName(userId);


                        switch (tableDao.checkNameToUpload(userId)){
                            case "Недзвецкий":
                            {
                                intSheetId = sheetId[0];
                                try{
                                    lineName = googleWrite.checkLastLine("Лист1");
                                } catch (java.io.IOException e){
                                    e.printStackTrace();
                                }

                                break;
                            }
                            case "":
                            {
                                intSheetId = sheetId[1];
                                try{
                                    lineName = googleWrite.checkLastLine("Лист3");
                                } catch (java.io.IOException e){
                                    e.printStackTrace();
                                }
                                break;
                            }
                            default:{
                                sendMsg(message, "Ошибка, данный ответственный отсутствует в таблице");
                                exists = false;
                            }
                        }
                        if (exists) {
                            sendMsg(message, "Записываю в Google Spreadsheet...");


                            try {
                                googleWrite.writeFirst(tableDao.checkNameToUpload(userId), userId, intSheetId, lineName);
                            } catch (java.io.IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                googleWrite.writeSecond(tableDao.checkTaskToUpload(userId), userId, intSheetId, lineName);
                            } catch (java.io.IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                googleWrite.writeThird(tableDao.checkDateToUpload(userId), userId, intSheetId, lineName);
                            } catch (java.io.IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                googleWrite.writeFourth(tableDao.checkIdToUpload(userId), userId, intSheetId, lineName);
                            } catch (java.io.IOException e) {
                                e.printStackTrace();
                            }
                        }
                        tableDao.closeConnection();
                        sendMsg(message, "Готово!");
                        sendNewMsg(message, " Введите ответственного");


                    }

                }
            } else {
                statesDao.stateInputUserId(userId);
                tableDao.closeConnection();
                sendNewMsg(message, "Введите ответственного");
            }
        }
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendNewMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendPeriodMsg(String text, Integer userId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userId.toString());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
