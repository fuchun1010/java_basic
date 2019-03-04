package com.tank.basic.dynamic;


import java.lang.annotation.*;

/**
 * @author fuchun
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DataSource {

  String sourceType();
}
