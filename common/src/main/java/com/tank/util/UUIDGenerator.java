package com.tank.util;

import lombok.val;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author fuchun
 * @date 2019-02-15
 */
public class UUIDGenerator {

  public static long generateUUID() {
    long id = uuid.incrementAndGet();
    if (id > UUID_INTERNAL) {
      synchronized (uuid) {
        if (uuid.get() >= id) {
          id -= UUID_INTERNAL;
          uuid.set(id);
        }
      }
    }
    return id;
  }

  public static void init(int serverNodeId) {
    UUIDGenerator.serverNodeId = serverNodeId;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date baseDate = null;
    try {
      baseDate = dateFormat.parse("2019-01-01");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(baseDate);
    val base = calendar.getTimeInMillis();
    val current = System.currentTimeMillis();
    val differ = (current - base) / 1000;
    uuid.addAndGet(differ);
  }


  private static AtomicLong uuid = new AtomicLong(1000);
  private static long serverNodeId = 1;
  private static long UUID_INTERNAL = 2000000000;


}
