package com.tank.basic.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

/**
 * @author tank198435163.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Student {

  public Student(String name, int age) {
    this.name = name;
    this.age = age;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Student)) {
      return false;
    }
    Student student = (Student) obj;
    return getAge() == student.getAge();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getAge());
  }

  private String name;

  private int age;

  private List<String> address;
}
