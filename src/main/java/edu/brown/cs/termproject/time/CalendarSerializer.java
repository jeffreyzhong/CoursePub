package edu.brown.cs.termproject.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public final class CalendarSerializer {

  private CalendarSerializer() throws IllegalAccessError {
    throw new IllegalAccessError("This class cannot be initialized.");
  }

  private static final SimpleDateFormat dateFormat =
      new SimpleDateFormat("yyyy-MM-dd");
  private static final SimpleDateFormat timeFormat =
      new SimpleDateFormat("hh:mm:ss");

  public static String toDate(Calendar calendar) {
    return dateFormat.format(calendar.getTime());
  }

  public static String toTime(Calendar calendar) {
    return timeFormat.format(calendar.getTime());
  }

  public static Long toMillis(String time) throws IllegalArgumentException {
    try {
      return timeFormat.parse(time).getTime();
    } catch (ParseException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static Calendar toCalendar(String time) {
    try {
      Calendar ret = Calendar.getInstance();
      ret.setTimeZone(TimeZone.getTimeZone("GMT"));
      ret.setTimeInMillis(timeFormat.parse(time).getTime());
      return ret;
    } catch (ParseException e) {
      throw new IllegalArgumentException(e);
    }
  }

  static {
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
  }
}
