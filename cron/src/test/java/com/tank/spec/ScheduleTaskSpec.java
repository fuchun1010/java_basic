package com.tank.spec;

import com.google.common.collect.Maps;
import com.tank.cron.DateUtil;
import com.tank.domain.ScheduleTask;
import com.tank.redis.RedisUtil;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Tuple;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ScheduleTaskSpec {

  @Test
  public void convert() {
    ScheduleTask taskA = new ScheduleTask();
    ScheduleTask taskB = new ScheduleTask();

    List<ScheduleTask> scheduleTasks = Arrays.asList(taskA, taskB);

    taskA.setDateStr("15:00:00");
    taskA.setStoreCode("0001");

    taskB.setDateStr("17:00:00");
    taskB.setStoreCode("0002");

    final List<ScheduleTask> data = scheduleTasks.stream().map(d -> {
      ScheduleTask newScheduleTask = new ScheduleTask();
      newScheduleTask.setStoreCode(d.getStoreCode());
      StringBuffer sb = new StringBuffer();
      sb.append(this.dateUtil.dateWithoutTimeFormatter().format(LocalDate.now().plusDays(1)));
      sb.append(" ");
      sb.append(d.getDateStr());
      String dateStrTime = sb.toString();
      LocalDateTime dateTime = LocalDateTime.from(this.dateUtil.dateWithTimeFormatter().parse(dateStrTime));
      newScheduleTask.setDateTime(dateTime);
      long differ = Duration.between(LocalDateTime.now(), dateTime).toMinutes() * 60;
      newScheduleTask.setDiffer(differ);
      return newScheduleTask;
    }).collect(Collectors.toList());

    Optional<Long> resultSetOpt = this.redisUtil.handleValue("submit:gzpszx:stores", (targetKey, redis) -> {
      Map<String, Double> maps = Maps.newHashMap();
      for (ScheduleTask task : data) {
        maps.putIfAbsent(task.getStoreCode(), new Double(task.getDiffer()));
      }
      long response = -1L;
      Pipeline pipeline = redis.pipelined();
      Response<Long> rs;
      try {
        pipeline.multi();
        rs = pipeline.zadd(targetKey, maps);
        pipeline.exec();

      } finally {
        pipeline.close();
      }
      response = rs != null ? rs.get() : -1;
      return response;
    });
    System.out.println(resultSetOpt.get());
    System.out.println("********");
  }

  @Test
  public void minScore() {
    Optional<Long> opt = this.redisUtil.handleValue(key, (targetKey, redis) -> {
      Pipeline pipeline = redis.pipelined();
      pipeline.multi();
      Response<Set<Tuple>> stores = pipeline.zrangeWithScores(targetKey, 0, -1);
      pipeline.exec();
      pipeline.close();
      Set<Tuple> tuples = stores.get();
      return new Double(tuples.iterator().next().getScore()).longValue();
    });
    System.out.println(opt.get());
  }

  @Test
  public void minStore() {
    double minScore = 102840;
    Optional<Set<String>> storesOpt = this.redisUtil.handleValue(this.key, (targetKey, redis) -> {
      Pipeline pipeline = redis.pipelined();
      pipeline.multi();
      Response<Set<String>> stores = pipeline.zrangeByScore(targetKey, 0d, minScore);
      pipeline.exec();
      pipeline.close();
      return stores.get();
    });
    storesOpt.get().stream().forEach(System.out::println);
  }

  @Before
  public void init() {
    this.dateUtil = DateUtil.build();
    this.redisUtil = RedisUtil.build();
  }

  private DateUtil dateUtil;

  private RedisUtil redisUtil;

  String key = "submit:gzpszx:stores";

}
