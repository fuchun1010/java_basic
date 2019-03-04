package com.tank.basic.dynamic;

import java.lang.annotation.*;

/**
 * @author fuchun
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SourceProcessor {
}
