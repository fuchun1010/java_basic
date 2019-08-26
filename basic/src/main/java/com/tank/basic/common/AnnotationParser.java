package com.tank.basic.common;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author tank198435163.com
 */
public class AnnotationParser {

  @SneakyThrows
  public static <I, R extends MyAnnotation> Optional<R> parsAnnotation(Class<I> clazz,Class<R> clazzA) {

    final I obj = clazz.getConstructor(new Class[]{}).newInstance(new Object[]{});

    Method[] methods = obj.getClass().getDeclaredMethods();

    for (Method method : methods) {
      R publishDate = method.getAnnotation(clazzA);
      if (publishDate != null) {



      }
    }
    return Optional.empty();
  }
}
