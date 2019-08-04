package com.tank.basic.util;

import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.val;
import redis.clients.jedis.params.SetParams;

import java.util.Map;
import java.util.Objects;

/**
 * @author fuchun
 */
public class RedisUtil {


  public static RedisPoolBuilder createRedisBuilder() {
    SingleRedis singleRedis = SingleRedis.SINGLE_REDIS;
    return singleRedis.builder().create();
  }

  public Map<String, Integer> currentLocker() {
    val ref = this.localCounter.get();
    if (Objects.nonNull(ref)) {
      return ref;
    }

    Map<String, Integer> newRef = Maps.newHashMap();
    this.localCounter.set(newRef);
    return localCounter.get();
  }

  public boolean lock(RedisPoolBuilder redisPoolBuilder, @NonNull final String key) {
    val ref = this.localCounter.get();
    if (ref == null) {
      boolean isOk = this._lock(redisPoolBuilder, key);
      if (isOk) {
        Map<String, Integer> tmpRef = Maps.newHashMap();
        tmpRef.putIfAbsent(key, 1);
        this.localCounter.set(tmpRef);
        return true;
      }
      return false;
    } else {
      int count = ref.get(key);
      count++;
      ref.put(key, count);
      return true;
    }


  }


  public boolean unLock(RedisPoolBuilder redisPoolBuilder, @NonNull final String key) {

    val ref = this.localCounter.get();
    if (ref == null) {
      return false;
    }
    Integer counter = ref.get(key);
    if (counter == null) {
      return false;
    }
    counter--;
    if (counter == 0) {
      ref.remove(key);
      this._unLock(redisPoolBuilder, key);
    } else {
      ref.put(key, counter);
    }

    return true;

  }

  private void _unLock(RedisPoolBuilder redisPoolBuilder, @NonNull final String key) {
    redisPoolBuilder.handleKey(key, (targetKey, redis) -> redis.del(targetKey));
  }

  private boolean _lock(RedisPoolBuilder redisPoolBuilder, @NonNull final String key) {
    SetParams setParams = new SetParams();
    setParams.nx().ex(50);
    String responseStatus = redisPoolBuilder.handleKey(key, (targetKey, redis) -> redis.set(targetKey, "lock", setParams));
    return Objects.nonNull(responseStatus);
  }

  private RedisUtil() {

  }

  private enum SingleRedis {
    SINGLE_REDIS;

    SingleRedis() {
      this.redisPoolBuilder = new RedisPoolBuilder();
    }

    private RedisPoolBuilder redisPoolBuilder;

    public RedisPoolBuilder builder() {
      return this.redisPoolBuilder;
    }

  }

  private ThreadLocal<Map<String, Integer>> localCounter = new ThreadLocal<>();

}
