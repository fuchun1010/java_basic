package com.tank.basic;

import com.tank.basic.play.HumanSortHelper;
import com.tank.basic.play.Teacher;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class TestHumanSortHelper {

  @Test
  public void testCompareTeacher() {
    Teacher a = new Teacher("a1", 27);
    Teacher b = new Teacher("b1", 20);
    Teacher c = new Teacher("c1", 32);
    List<Teacher> teachers = Arrays.asList(a, b, c);
    teachers.sort((f, s) -> Integer.compare(f.getAge(), s.getAge()));
    teachers.stream().map(teacher -> teacher.getName()).forEach(System.out::println);
  }

  @Test
  public void testCompareTeacher2() {
    Teacher a = new Teacher("a1", 27);
    Teacher b = new Teacher("b1", 20);
    Teacher c = new Teacher("c1", 32);
    List<Teacher> teachers = Arrays.asList(a, b, c);
    HumanSortHelper humanSortHelper = new HumanSortHelper();
    List<String> names = humanSortHelper
        .sorted(teachers, (f, s) -> Integer.compare(f.getAge(), s.getAge()))
        .stream()
        .map(Teacher::getName)
        .collect(Collectors.toList());
    for (String name : names) {
      System.out.println(name);
    }

  }

  @Test
  public void testMaxLongValue() {
    LongStream.range(100, 1000).boxed().map(d -> Long.MAX_VALUE - d).forEach(System.out::println);
  }

}
