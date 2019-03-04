package com.tank.basic.dynamic;


import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @param <T>
 * @author fuchun
 */
@DataSource(sourceType = "kafka")
public class KafkaProcessor<T> {

  /**
   * @param data
   * @throws Exception
   */
  @SourceProcessor
  public void done(final T data) throws Exception {
    Object[] parameters = new Object[]{data};
    boolean isValidate = Arrays.stream(parameters)
        .map(Objects::nonNull)
        .reduce(true, Boolean::logicalAnd);

    if (isValidate) {
      System.out.println("data = [" + data + "]");
    } else {
      throw new InvalidParameterException("parameter not allowed empty");
    }

  }
}
