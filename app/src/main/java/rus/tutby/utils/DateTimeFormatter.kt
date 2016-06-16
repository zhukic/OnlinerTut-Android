package rus.tutby.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


class DateTimeFormatter() {

    companion object {
        val INPUT_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss"
        val SHORT_DATE_FORMAT = "dd.MM, HH:mm"
        val LONG_DATE_FORMAT = "EEE, dd:MMM:yyyy, HH:mm:ss"

        fun getLongFormattedDate(dateIn: String): String {
            val formatter: SimpleDateFormat = SimpleDateFormat(INPUT_DATE_FORMAT, Locale.ENGLISH);
            val date: Date = formatter.parse(dateIn)
            formatter.applyPattern(LONG_DATE_FORMAT)
            return formatter.format(date);
        }

        fun getShortFormattedDate(dateIn: String): String {
            val formatter: SimpleDateFormat = SimpleDateFormat(INPUT_DATE_FORMAT, Locale.ENGLISH);
            val date: Date = formatter.parse(dateIn);
            formatter.applyPattern(SHORT_DATE_FORMAT)
            return formatter.format(date);
        }
    }

}
