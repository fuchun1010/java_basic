package com.tank.basic.thread;

import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * @author tank198435163.com
 */
public class ThreadTest {

  //  public static void main(String[] args) {
//    Thread childThread = new Thread(() -> {
//      while (true) {
//        System.out.println("I am child thread");
//        try {
//          TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
//      }
//    });
//
//    Thread mainThread = new Thread(new Runnable() {
//      @Override
//      public void run() {
//        childThread.setDaemon(true);
//        childThread.start();
//        System.out.println("I'm main thread...");
//      }
//    });
//    mainThread.start();
//
//  }

  @Test
  public void testConvert() {
    long rs = TimeUnit.SECONDS.convert(2, TimeUnit.HOURS);
    System.out.println(rs);
  }

  @Test
  public void testDiv1() {
    int d = 31;
    int factor = 4;
    //mask = wheel.length - 1
    //因为一圈的长度为2的n次方，mask = 2^n-1后低位将全部是1，然后deadline&mast
    int rs = d & (factor - 1);
    int rs2 = d % factor;
    int rs3 = Math.floorMod(d, factor);
    System.out.println(rs);
    System.out.println(rs2);
    System.out.println(rs3);
  }


  @Test
  public void testNormalize() {
    int rs = normalizeTicksPerWheel(60);
    int rs2 = this.normalizeTicks(60);
    System.out.println(rs);
    System.out.println(1 << 30);
  }

  @Test
  public void testXX() {
    CoderA coderA = new CoderA();
    Programmer programmer = ((Programmer) Proxy.newProxyInstance(
        coderA.getClass().getClassLoader(),
        coderA.getClass().getInterfaces(),
        (proxy, method, args) -> {
          String methodName = method.getName();
          if ("coding".equalsIgnoreCase(methodName)) {
            System.out.println("this is proxy !!!");
          }
          return method.invoke(coderA, args);
        }));
    programmer.coding();
  }

  public int normalizeTicks(int ticksPerWheel) {
    int n = ticksPerWheel - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    // 这里1073741824 = 2^30,防止溢出
    return (n < 0) ? 1 : (n >= 1073741824) ? 1073741824 : n + 1;
  }


  private static int normalizeTicksPerWheel(int ticksPerWheel) {
    int normalizedTicksPerWheel = 1;
    while (normalizedTicksPerWheel < ticksPerWheel) {
      normalizedTicksPerWheel <<= 1;
    }
    return normalizedTicksPerWheel;
  }

}
