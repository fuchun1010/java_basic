package com.tank.basic.ds;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tank.basic.vo.Student;
import lombok.Data;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BodyReflectionTest {

  @Test
  public void dynamicGenerator() throws Exception {
    Map<String, Object> data = Maps.newHashMap();
    data.put("name", "lisi");
    data.put("age", 11);
    List<String> address = Lists.newLinkedList();
    data.put("address", address);
    address.add("bj");
    address.add("cq");
    Student mp5 = to(data, Student.class);
    System.out.println(mp5);

  }

  public static <R> R to(Map<String, Object> maps, Class<R> clazz) throws Exception {
    Map<String, Method> methods = Maps.newHashMap();

    for (Method method : clazz.getDeclaredMethods()) {

      String methodName = method.getName();
      if (methodName.indexOf("set") != -1) {
        methods.put(methodName.substring(3, methodName.length()).toLowerCase(), method);
      }

    }
    R instance = clazz.newInstance();
    for (Map.Entry<String, Method> methodEntry : methods.entrySet()) {
      String key = methodEntry.getKey();
      Object value = maps.get(key);

      if (value == null) {
        continue;
      }

      boolean isBasicClass = value.getClass() == Integer.class || value.getClass() == String.class || value.getClass() == Long.class;
      if (isBasicClass) {
        methodEntry.getValue().invoke(instance, value);
      } else {
        if (value.getClass() == LinkedList.class) {
          LinkedList lists = ((LinkedList) value);
          List collections = Lists.newLinkedList();
          for (Object element : lists) {
            if (element.getClass() == Integer.class || element.getClass() == String.class) {
              collections.add(element);

            } else {
              Object xx = to((Map<String, Object>) element, element.getClass());
              collections.add(xx);
            }

          }
          methodEntry.getValue().invoke(instance, collections);
        } else {
          Object tmpRs = to(((Map) value), value.getClass());
          methodEntry.getValue().invoke(instance, tmpRs);
        }
        System.out.println("xxx");
      }
    }

    return instance;
  }

  @Data
  private static class Mp5 {


    private List<Student> students;

  }

}
