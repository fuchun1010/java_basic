package com.tank.cron;

import lombok.NonNull;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @author fuchun
 */
public class DateUtil {

  public String convert2(@NonNull final TemporalAccessor temporalAccessor) {
    return this.dateWithTimeFormatter().format(temporalAccessor);
  }

  public DateTimeFormatter dateWithTimeFormatter() {
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return df;
  }

  public static DateUtil builder() {
    return new DateUtil();
  }

  private DateUtil() {

  }

  private final String datePattern = "yyyy-MM-dd HH:mm:ss";
}
