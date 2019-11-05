package com.tank.basic.ds;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class GenericArrayUtilsTest {

  @Test
  public void testIntArr() {
    GenericArray<Integer> intArrInstance = new GenericArray<>();
    intArrInstance.initArray(Integer.class);

    for (int i = 0; i < 60; i++) {
      intArrInstance.replaceElement(i, i + 1);
    }
    intArrInstance.printArray();
  }

  private static class GenericArray<T> {

    public GenericArray() {

    }

    public GenericArray(int capacity) {
      boolean illegal = capacity <= 0 || capacity > Integer.MAX_VALUE;
      this.capacity = capacity;
    }

    public GenericArray<T> initArray(Class<T> clazz) {
      this.arr = (T[]) Array.newInstance(clazz, this.capacity);
      return this;
    }

    public synchronized void replaceElement(@NonNull final T element, int index) {
      boolean illegal = index < 0 || index > Integer.MAX_VALUE || index > this.capacity;
      if (illegal) {
        throw new IllegalArgumentException("index beyond validation range");
      }
      this.arr[index] = element;
      this.enhanceArray(element);
    }

    public synchronized void addElement(@NonNull final T element, int index) {
      //TODO insert head
      //TODO middle
      //TODO insert tail
      boolean isEmptyArr = this.length.get() == 0;
      if (isEmptyArr) {
        this.replaceElement(element, index);
      }
      enhanceArray(element);
    }

    private void enhanceArray(@NonNull T element) {
      int currentLen = length.incrementAndGet();
      int halfLen = this.capacity >> 1;
      if (currentLen > halfLen) {
        //enhance array length when over half length when inserted data
        int newCapacity = this.capacity << 1;
        T[] newTargetArr = (T[]) Array.newInstance(element.getClass(), newCapacity);
        System.out.println("enhance");
        System.arraycopy(this.arr, 0, newTargetArr, 0, this.capacity);
        this.arr = newTargetArr;
        this.capacity = newCapacity;
      }
    }

    public void printArray() {
      Arrays.stream(this.arr).filter(Objects::nonNull).forEach(System.out::println);
    }

    @SneakyThrows
    public boolean isNotEnough() {
      boolean illegal = Objects.isNull(this.arr) ? true : false;
      if (illegal) {
        throw new NullPointerException("Generic Array not been init");
      }
      return false;
    }

    public T[] fetchArray() {
      return this.arr;
    }

    private AtomicInteger length = new AtomicInteger(0);

    private int capacity = 32;

    private T[] arr = null;
  }

}
