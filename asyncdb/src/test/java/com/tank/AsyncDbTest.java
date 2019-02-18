package com.tank;

import com.tank.domain.Person;
import io.reactivex.Flowable;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.val;
import org.davidmoten.rx.jdbc.Database;
import org.davidmoten.rx.jdbc.Parameter;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AsyncDbTest {

  @Before
  public void init() {
    this.asyncDb = AsyncDb.createDbInstance();
    this.db = this.asyncDb.createAsyncDbInstance();
  }

  @Test
  public void createAsyncDbInstance() {
    Database db = this.asyncDb.createAsyncDbInstance();
    assertNotNull(db);
  }

  @Test
  public void personCount() {
    db.select(this.countSql).getAs(Integer.class).blockingForEach(System.out::println);
  }

  @Test
  public void personCountWithBlockFirst() {
    val count = db.select(this.countSql).getAs(Integer.class).blockingFirst();
    assertTrue(count.compareTo(2) == 0);
  }

  @Test
  public void testSelectedAllSql() {
    Flowable<Person> flowable = db.select(selectedAllSql).get(rs -> {
      Person person = new Person();
      person.setId(rs.getInt("id"));
      person.setName(rs.getString("pname"));
      person.setGender(rs.getByte("gender"));
      person.setBirth(rs.getDate("birth"));
      return person;
    });

    flowable.blockingForEach(System.out::println);
  }

  @Test
  public void testSelectedByNotExistsCondition() {
    Flowable<Person> flowable = this.db.select(selectedByNotExistsCondition)
        .parameter(Parameter.create("name", "caocao"))
        .get(rs -> {
          Person person = new Person();
          person.setId(rs.getInt("id"));
          person.setName(rs.getString("pname"));
          person.setGender(rs.getByte("gender"));
          person.setBirth(rs.getDate("birth"));
          return person;
        });

    flowable.doOnNext(person -> {
      String tips = CompletableFuture.supplyAsync(() -> person).handleAsync((p, err) -> p.toString()).join();
      System.out.println(tips);
    }).blockingSubscribe();

  }

  @Test
  public void testInsertWithReturnId() {
    val insertSql = "insert into tab_persons(pname, gender, birth) values(?,?,str_to_date(?, '%Y-%m-%d'))";
    val id = this.db.update(insertSql)
        .parameters("liubei", new Integer(1), "1981-03-04")
        .returnGeneratedKeys()
        .getAs(Integer.class)
        .blockingFirst();
    System.out.println("id =" + id);
  }

  @Test
  public void testDeleteById() {
    val deletedSql = "delete from tab_persons where id = 3";
    val affectedRows = this.db.update(deletedSql).transaction().count().blockingGet();
    System.out.println("affectedRows =" + affectedRows);
  }

  @Test
  public void changedBirthWithById() {
    val updatedSql = "update tab_persons set pname = :name where id = :id";
    val affectedRows = this.db.update(updatedSql).parameters(Parameter.create("name", "x1")
        , Parameter.create("id", 1)).transaction().doOnNext(cnt -> {
      throw new Exception("xxx");
    }).count().blockingGet();
    System.out.println("affectedRows =" + affectedRows);
  }

  @Test
  public void returnFluxResponse() {
    Flowable<Person> flowable = this.db.select(selectedAllSql).get(rs -> {
      Person person = new Person();
      person.setId(rs.getInt("id"));
      person.setName(rs.getString("pname"));
      person.setGender(rs.getByte("gender"));
      person.setBirth(rs.getDate("birth"));
      return person;
    });
    Mono<List<Person>> persons = Flux.from(flowable).collectList();
    persons.block().forEach(System.out::println);
  }

  @Test
  public void returnMonoResponse() {
    val sql = "select id,pname, gender, birth from tab_persons where id = :id";
    Flowable<Person> flowable = this.db.select(sql)
        .parameter(Parameter.create("id", 1))
        .get(rs -> {
          Person person = new Person();
          person.setId(rs.getInt("id"));
          person.setName(rs.getString("pname"));
          person.setGender(rs.getByte("gender"));
          person.setBirth(rs.getDate("birth"));
          try {
            Thread.sleep(50000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          return person;
        });
    Person person = Mono.from(flowable).block(Duration.ofSeconds(2));
    Optional.<Person>ofNullable(person).ifPresent(System.out::println);
  }


  private AsyncDb asyncDb;
  private Database db;
  private final String countSql = "select count(id) as cnt from tab_persons";
  private final String selectedAllSql = "select id,pname, gender, birth from tab_persons";
  private final String selectedByNotExistsCondition = "select id,pname, gender, birth from tab_persons t where  pname = :name";
}