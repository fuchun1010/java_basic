package com.tank.basic.play;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tank198435163.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher implements Human {

  private String name;

  private Integer age;
}
