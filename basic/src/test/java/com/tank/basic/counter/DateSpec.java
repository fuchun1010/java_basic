package com.tank.basic.counter;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateSpec {

  @Test
  public void testPassedDays() {
    //假设今天的日期是"2019-07-01"
    String dateStr = "2019-07-01";
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.from(df.parse(dateStr));
    //获取2019-07-01是2019年的第多少天
    int passedDays = localDate.getDayOfYear();
    //1年365天，0 ~ 364位
    byte[] dateArr = new byte[365];
    //假设是第187天，那么数组的下标是186的就赋值 = 1，表示2019-07-01进店了
    //可以把值等于1的index取出来，反向计算对应的日期(这个代码5行左右，最后把这个365个字节的数据存redis就完了)
    dateArr[passedDays - 1] = 1;

  }


}
