package com.tank.observer;

import org.junit.Before;
import org.junit.Test;

public class DataSenderTest {

  @Before
  public void init() {
    this.redisReceiver = new RedisReceiver();
    this.kafkaReceiver = new KafkaReceiver();
    this.dataSender = new DataSender();
    this.dataSender.addObserver(this.redisReceiver);
    this.dataSender.addObserver(this.kafkaReceiver);
  }

  @Test
  public void send() {
    this.dataSender.send("hello");
  }

  private RedisReceiver redisReceiver;

  private KafkaReceiver kafkaReceiver;

  private DataSender dataSender;
}