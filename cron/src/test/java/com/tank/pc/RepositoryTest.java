package com.tank.pc;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class RepositoryTest {

  @Test
  public void fetchMinElements() {
    double rs = this.repository.fetchMinElements().flatMap(p -> Optional.ofNullable(p.getScore())).orElse(-1d);
    System.out.println(rs);
  }

  @Test
  public void remove() {
    long rs = this.repository.fetchMinElements().flatMap(p -> Optional.ofNullable(repository.remove(p.getScore(), p.getScore()))).orElse(-1L);
    System.out.println(rs);
  }

  @Before
  public void init() {
    this.repository = new Repository();
  }

  private Repository repository;
}