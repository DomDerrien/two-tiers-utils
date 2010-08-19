package domderrien.i18n;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DateUtils {

    /**
     * Get the current date for the local time zone
     * @return Current date/time
     */
    public static Calendar getNowCalendar() {
        return new GregorianCalendar();
    }

    /**
     * Get the current date for the local time zone
     * @return Current date/time
     */
    public static Date getNowDate() {
        return getNowCalendar().getTime();
    }

    // See documentation on http://download.oracle.com/docs/cd/E17409_01/javase/6/docs/api/java/text/SimpleDateFormat.html

    // This is the ISO format for Dojo application
    public final static String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateFormat isoFormatter = new SimpleDateFormat (ISO_DATE_FORMAT, Locale.US);

    // This is the ISO format for Dojo application
    public final static String YMD_DATE_FORMAT = "yyyy-MM-dd";
    private static final DateFormat ymdFormatter = new SimpleDateFormat (YMD_DATE_FORMAT, Locale.US);

    private static final Map<String, DateFormat> formatters = new HashMap<String, DateFormat>();
    static {
        formatters.put(Locale.US.toString() + "_" + ISO_DATE_FORMAT, isoFormatter);
        formatters.put(Locale.US.toString() + "_" + YMD_DATE_FORMAT, ymdFormatter);
    }

    /**
     * Transform the given date in an ISO formatted string
     * @param timeInMilliseconds date to transform
     * @return ISO representation of the given date
     */
    public static String millisecondsToISO(long timeInMilliseconds) {
        return dateToISO(new Date(timeInMilliseconds));
    }

    /**
     * Transform the given date in an ISO formatted string
     * @param date date to transform
     * @return ISO representation of the given date
     */
    public static String dateToISO(Date date) {
        return isoFormatter.format(date);
    }

    /**
     * Transform the given date in an YYYY-MM-DD formatted string
     * @param date date to transform
     * @return YYYY-MM-DD representation of the given date
     */
    public static String dateToYMD(Date date) {
        return ymdFormatter.format(date);
    }

    /**
     * Transform the given date in as specified by the given format
     * @param date date to transform
     * @param format date formatter, as given to the <code>DateFormat</code> constructor
     * @param locale the locale whose date format symbols should be used
     * @return Expected representation of the given date
     * @see java.text.SimpleDateFormat
     */
    public static String dateToCustom(Date date, String format, Locale locale) {
        String formatterKey = locale.toString() + "_" + format;
        DateFormat formatter = formatters.get(formatterKey);
        if (formatter == null) {
            formatter = new SimpleDateFormat(format, locale);
            formatters.put(formatterKey, formatter);
        }
        return formatter.format(date);
    }

    /**
     * Extract the date represented by the given ISO string
     * @param iso ISO representation of a date
     * @return Date in milliseconds
     * @throws ParseException if the given string does not have the expected ISO format
     */
    public static long isoToMilliseconds(String iso) throws ParseException {
        return isoToDate(iso).getTime();
    }

    /**
     * Extract the date represented by the given ISO string
     * @param iso ISO representation of a date
     * @return Date
     * @throws ParseException if the given string does not have the expected ISO format
     */
    public static Date isoToDate(String iso) throws ParseException {
        if (iso == null || iso.length() == 0) {
            throw new ParseException("Cannot unserialize an empty ISO string", 0);
        }
        isoFormatter.setCalendar(new GregorianCalendar());
        return isoFormatter.parse(iso);
    }

}
