package com.tank.domain;

import lombok.Data;
import lombok.val;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author fuchun
 */
@Data
public class Person {

  private long id;

  private String name;

  private byte gender;

  private Date birth;

  @Override
  public String toString() {
    val sb = new StringJoiner(",");
    sb.add(String.valueOf(this.id));
    sb.add(this.name);
    sb.add(gender == 1 ? "male" : "female");
    if (Objects.nonNull(birth)) {
      LocalDate localDate = birth.toLocalDate();
      DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      val date = localDate.format(df);
      sb.add(date);
    }

    return sb.toString();
  }
}
