package com.tank.basic.binary;

import com.sun.istack.internal.NotNull;
import com.tank.basic.vo.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryTest {


  @Before
  public void init() {
    data = new int[]{1, 2, 11, 19, 21, 27};
    List<Student> studentList = Arrays.stream(data).boxed().map(i -> {
      String name = String.format("x_%d", i);
      return new Student(name, i);
    }).collect(Collectors.toList());
    students = new Student[studentList.size()];
    students = studentList.toArray(students);
  }

  @Test
  public void testBinarySearch1() {
    int target = 11;
    boolean rs = this.search(data, target);
    Assert.assertTrue(rs);
  }

  @Test
  public void testBinarySearch2() {
    int target = 18;
    boolean rs = this.search(data, target);
    Assert.assertFalse(rs);
  }

  @Test
  public void testBinarySearch3() {
    int target = 27;
    boolean rs = this.search(data, target);
    Assert.assertTrue(rs);
  }

  @Test
  public void testBinarySearch4() {
    int target = 1;
    boolean rs = this.search(data, target);
    Assert.assertTrue(rs);
  }

  @Test
  public void testBinarySearch5() {
    data = new int[]{19, 21, 27};
    int target = 27;
    boolean rs = this.search(data, target);
    Assert.assertTrue(rs);
  }

  @Test
  public void testBinarySearch6() {
    int a = 65;
    int b = 76;
    System.out.println(Integer.compare(a, b));
    System.out.println(Integer.compare(b, a));
  }


  @Test
  public void testBinarySearch7() {
    Student target = new Student().setName("target").setAge(11);
    boolean existed = this.search(students, target, Comparator.comparingInt(Student::getAge), Student.class);
    Assert.assertTrue(existed);
  }

  @Test
  public void testBinarySearch8() {
    Integer[] data = new Integer[]{1, 2, 11, 19, 21, 27};
    boolean existed = this.search(data, 19, Comparator.comparingInt(a -> a), Integer.class);
    Assert.assertTrue(existed);
  }

  @Test
  public void testBinarySearch9() {
    Integer[] data = new Integer[]{1, 21, 11, 19, 2, 27, 31, 63};
    boolean existed = this.search(data, 99, Comparator.comparingInt(a -> a), Integer.class);
    Assert.assertFalse(existed);
  }

  @Test
  public void testBinarySearch10() {
    int target = 63;
    Integer[] data = new Integer[]{1, 21, 11, 19, 2, 27, 31, 63};
    boolean existed = this.search(data, target, Comparator.comparingInt(a -> a), Integer.class);
    Assert.assertTrue(existed);
  }

  @Test
  public void testBinarySearch11() {
    int target = 1;
    Integer[] data = new Integer[]{1, 21, 11, 19, 2, 27, 31, 63};
    boolean existed = this.search(data, target, Comparator.comparingInt(a -> a), Integer.class);
    Assert.assertTrue(existed);
  }


  public <T> boolean search(@NotNull final T[] dataArr, T target, Comparator<T> comparator, Class<T> clazz) {
    T tmpData[] = null;
    if (dataArr.length == 1) {
      return target.equals(dataArr[0]);
    }
    int len = dataArr.length;
    int start = 0;
    int middleIndex = new Double(Math.ceil((len - start) / 2)).intValue();
    T tmpValue = dataArr[middleIndex];

    if (comparator.compare(tmpValue, target) == 1) {
      //copy left array data
      int offsetPosition = dataArr.length - middleIndex > 1 ? middleIndex + 1 : middleIndex;
      tmpData = (T[]) (Array.newInstance(clazz, offsetPosition));
      System.arraycopy(dataArr, start, tmpData, 0, tmpData.length);
    } else {
      //copy right array data
      tmpData = (T[]) (Array.newInstance(clazz, dataArr.length - middleIndex));
      System.arraycopy(dataArr, middleIndex, tmpData, 0, tmpData.length);
    }
    //research
    boolean rs = this.search(tmpData, target, comparator, clazz);
    if (rs) {
      return true;
    }
    return false;

  }

  public boolean search(@NotNull final int[] data, int target) {
    int tmpData[] = null;
    if (data.length == 1) {
      return data[0] == target;
    }
    int len = data.length;
    int start = 0;
    int middleIndex = new Double(Math.ceil((len - start) / 2)).intValue();
    int tmpValue = data[middleIndex];

    if (tmpValue > target) {
      //copy left array data
      int offsetPosition = data.length - middleIndex > 1 ? middleIndex + 1 : middleIndex;
      tmpData = new int[offsetPosition];
      System.arraycopy(data, start, tmpData, 0, tmpData.length);
    } else {
      //copy right array data
      tmpData = new int[data.length - middleIndex];
      System.arraycopy(data, middleIndex, tmpData, 0, tmpData.length);
    }
    //research
    boolean rs = this.search(tmpData, target);
    if (rs) {
      return true;
    }
    return false;

  }

  private int[] data;

  private Student[] students;

}
