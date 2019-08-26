package com.tank.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * @author fuchun
 */
public class Basic {


  public static void main(String[] args) {

    Address address = new Address("Bj", "caoyang");
    if (address != null) {
      new Thread(new Police(address), "police").start();
      new Thread(new Assist(address), "assist").start();

    }

  }


  @Data
  @AllArgsConstructor
  public static class Address {
    private String city;
    private String street;
  }

  @Data
  @AllArgsConstructor

  static class Assist implements Runnable {

    @Override
    @SneakyThrows
    public void run() {
      System.out.println("assist help query address * " + Thread.currentThread().getName());
      synchronized (address) {
        TimeUnit.MILLISECONDS.sleep(200);
        System.out.println("..");
        address.notifyAll();
      }
    }

    private Address address;
  }

  @Data
  @AllArgsConstructor
  static class Police implements Runnable {

    @Override
    @SneakyThrows
    public void run() {
      System.out.println(Thread.currentThread().getName());
      synchronized (address) {
        address.wait();

        System.out.println("police arrest thief * " + Thread.currentThread().getName());
      }
    }

    private Address address;

  }

}
