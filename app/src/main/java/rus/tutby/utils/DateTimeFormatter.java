package rus.tutby.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeFormatter {

    private static final String INPUT_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss";

    private static final String SHORT_DATE_FORMAT = "dd.MM, HH:mm";

    public static String getLongFormattedDate(String dateIn){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(INPUT_DATE_FORMAT, Locale.ENGLISH);
            Date date = formatter.parse(dateIn);
            DateFormat formatter2 = DateFormat.getDateTimeInstance(
                    DateFormat.LONG,
                    DateFormat.SHORT,
                    new Locale("ru"));
            return formatter2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getShortFormattedDate(String dateIn){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(INPUT_DATE_FORMAT, Locale.ENGLISH);
            Date date = formatter.parse(dateIn);
            formatter.applyPattern(SHORT_DATE_FORMAT);
            return formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
