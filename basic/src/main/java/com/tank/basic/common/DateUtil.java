package com.tank.basic.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author tank198435163.com
 */
public class DateUtil {

  public static String currentDateStrWithoutTime() {
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return df.format(LocalDate.now());
  }

  public static String currentDateStrWithTime() {
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return df.format(LocalDateTime.now());
  }
}
