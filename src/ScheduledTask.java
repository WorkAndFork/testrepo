import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import java.util.Calendar;
import java.util.Calendar.Builder;
import java.util.GregorianCalendar;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;


public class ScheduledTask extends TimerTask {
        int i = 0;
        Date now = new Date();
        Calendar date = Calendar.getInstance();
        Test mainTest = new Test();
        SimpleDateFormat checkDate = new SimpleDateFormat("dd.MM.yyyy");
        String todayDate = checkDate.format(now);
        String tomorrowDate = checkDate.format(yesterday());
        TableDAO tableDao = new TableDAO();
        GregorianCalendar start = new GregorianCalendar(2017,Calendar.JUNE,07,14,15,00);


        private Date yesterday(){
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE,+1);
            return cal.getTime();
        }

        @Override
        public void run() {

        for (i = 0; i < tableDao.containAllDates().size()-1; i ++)
        {
            if (tableDao.containAllDates().get(i).equals(tomorrowDate))
            {
               mainTest.sendPeriodMsg( "Задача на сегодня: " + tableDao.getTodayDateTasks().get(i), (tableDao.getTodayDateUserId().get(i)));
            }
        }
        }

}
