package com.tank.basic.guess;

import lombok.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author tank198435163.com
 */
public abstract class CacheAccess {

  /**
   * generate redis key by different sub class
   *
   * @param key
   * @return
   */
  protected abstract String keyWrapper(@NonNull final String key);


  /**
   * operate multi key at once
   * please take care input key order
   *
   * @param keyOperator
   * @param keys
   * @return
   */
  public boolean handleKey(@NonNull final Consumer<List<String>> keyOperator, @NonNull String... keys) {

    Optional<String> checkedOpt = this.check(keys, k -> keys.length == 0, "key not allowed empty");
    if (checkedOpt.isPresent()) {
      throw new IllegalArgumentException(checkedOpt.get());
    }
    try {
      List<String> redisKeys = Arrays.stream(keys).collect(Collectors.toList());
      keyOperator.accept(redisKeys);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

  }


  public boolean handleKey(@NonNull String key, @NonNull final Consumer<String> keyOperator) {
    Optional<String> checkedOpt = this.check(key, k -> k.trim().length() == 0, "key not allowed empty");
    if (checkedOpt.isPresent()) {
      throw new IllegalArgumentException(checkedOpt.get());
    }
    String redisKey = this.keyWrapper(key);
    try {
      keyOperator.accept(redisKey);
      //TODO may be expired redis key with fixed date
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private <T> Optional<String> check(T data, Predicate<T> predicate, @NonNull final String message) {
    return predicate.test(data) ? Optional.ofNullable(message) : Optional.empty();
  }
}
