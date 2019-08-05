package com.tank.cron;

import com.cronutils.model.time.ExecutionTime;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public class CronHandlerTest {

  @Test
  public void handleCron() {
    DateUtil dateUtil = DateUtil.builder();
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
      DateUtil dateUtil = DateUtil.builder();
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

  

  @Before
  public void init() {
    this.cronHandler = new CronHandler();
  }

  private CronHandler cronHandler;

  private final String EMPTY_STR = "-";

  private String cronStrExp = "15 8,9,10 * * *";

}