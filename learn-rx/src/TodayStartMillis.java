import java.util.Calendar;
import java.util.Date;

/**
 * Created by naraykan on 22/02/17.
 */
public class TodayStartMillis {
    public static void main(String args[]) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, -(c.get(Calendar.HOUR_OF_DAY)));
        c.add(Calendar.MINUTE, -(c.get(Calendar.MINUTE)));
        c.add(Calendar.SECOND, -(c.get(Calendar.SECOND)));
        c.add(Calendar.MILLISECOND, -(c.get(Calendar.MILLISECOND)));
        long millis = c.getTimeInMillis();
        System.out.println(millis);

        Date d = new Date(millis);
        System.out.println(d);
    }
}
