package com.tank.cron;

import com.cronutils.model.time.ExecutionTime;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class CronHandlerTest {

  @Test
  public void handleCron() {
    Optional<String> nextExecuteTime = Optional.empty();
    try {
      nextExecuteTime = this.cronHandler.handleCron(this.cronStrExp, cron -> {
        ZonedDateTime now = ZonedDateTime.now();
        ExecutionTime executionTime = ExecutionTime.forCron(cron);
        String dateStr = executionTime.nextExecution(now)
            .flatMap(d -> Optional.ofNullable(dateUtil.convert2(d)))
            .orElse(EMPTY_STR);

        return dateStr;
      });

    } catch (Exception e) {
      System.out.println("--:" + e.getMessage());
    }

    Assert.assertTrue(nextExecuteTime.isPresent());
    Assert.assertTrue(!nextExecuteTime.get().equalsIgnoreCase(EMPTY_STR));
    System.out.println(nextExecuteTime.get());

  }

  @Test
  public void testPreviewFiveDays() {
    Optional<List<String>> descOpt = this.cronHandler.handleCron(this.cronStrExp, cron -> {
      List<String> lists = Lists.newLinkedList();
      for (int i = 1; i <= 5; i++) {
        ZonedDateTime start = ZonedDateTime.now().plusDays(i);
        ExecutionTime executionTime = ExecutionTime.forCron(cron);
        String dateStr = executionTime.nextExecution(start)
            .flatMap(d -> Optional.ofNullable(dateUtil.convert2(d)))
            .orElse(EMPTY_STR);
        lists.add(dateStr);
      }
      return lists;
    });
    Assert.assertTrue(descOpt.isPresent());
    descOpt.get().stream().forEach(System.out::println);
  }

  @Test
  public void testDiffFromNextExecutionWithSecond() {
    Optional<Long> diffOpt = this.cronHandler.handleCron(this.cronStrExp, cron -> {
      ZonedDateTime now = ZonedDateTime.now();
      ExecutionTime executionTime = ExecutionTime.forCron(cron);
      long differ = executionTime.timeToNextExecution(now).flatMap(d -> Optional.ofNullable(d.toMinutes() * 60)).orElse(0L);
      return differ;
    });
    Assert.assertTrue(diffOpt.isPresent());
    Assert.assertTrue(diffOpt.get() > 0);
    System.out.println("differ : " + diffOpt.get());
  }


  @Test
  @SneakyThrows
  public void testInterval() {
    LocalDateTime now = LocalDateTime.now();
    String start = this.dateUtil.dateWithTimeFormatter().format(now);
    System.out.println("start = " + start);
    TimeUnit.SECONDS.sleep(10);
    now = LocalDateTime.now();
    String end = this.dateUtil.dateWithTimeFormatter().format(now);
    System.out.println("end = " + end);
  }


  @Before
  public void init() {
    this.cronHandler = new CronHandler();
    this.dateUtil = DateUtil.build();
  }

  private CronHandler cronHandler;

  private DateUtil dateUtil;

  private final String EMPTY_STR = "-";

  private String cronStrExp = "15 8,9,10 * * *";

}