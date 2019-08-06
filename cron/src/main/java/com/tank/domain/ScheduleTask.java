package com.tank.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author fuchun
 */
@Data
@Accessors(chain = true)
public class ScheduleTask {


  public boolean isOverNow() {
    return dateTime != null && dateTime.isAfter(LocalDateTime.now());
  }

  private LocalDateTime dateTime;

  private String storeCode;

  private String dateStr;

  private long differ;

}
