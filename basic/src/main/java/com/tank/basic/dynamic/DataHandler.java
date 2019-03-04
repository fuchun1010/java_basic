package com.tank.basic.dynamic;

import com.google.common.base.Preconditions;
import com.google.common.reflect.Reflection;
import lombok.val;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * @author tank198435163.com
 */
public class DataHandler {

  public String fetchProcessorPackagePath() {
    String packageName = this.getClass().getPackage().getName();
    return packageName;
  }


  public void scanProcessor() {
    val packagePath = this.fetchProcessorPackagePath();
    Reflections reflections = new Reflections(packagePath);
    Set<Class<?>> clazzes = reflections.getTypesAnnotatedWith(DataSource.class);
    Preconditions.checkArgument(Objects.nonNull(clazzes));
    Preconditions.checkArgument(clazzes.size() > 0);
    Iterator<Class<?>> iterator = clazzes.iterator();
    while (iterator.hasNext()) {
      Class<?> clazz = iterator.next();
      Method[] methods = clazz.getMethods();
      for (Method method : methods) {
        Annotation[] annotations = method.getAnnotations();
        boolean isValidate = Objects.nonNull(annotations) && annotations.length > 0;
        if (isValidate) {
          for (Annotation annotation : annotations) {
            Class<? extends Annotation> xx = annotation.annotationType();
            if (xx.equals(SourceProcessor.class)) {
              try {
                method.invoke(clazz.newInstance(), "kafka process data");
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
          }
        } else {
          break;
        }

      }

    }
  }

}
