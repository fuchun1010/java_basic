package com.tank;

import lombok.val;
import org.davidmoten.rx.jdbc.ConnectionProvider;
import org.davidmoten.rx.jdbc.Database;
import org.davidmoten.rx.jdbc.pool.DatabaseType;
import org.davidmoten.rx.jdbc.pool.NonBlockingConnectionPool;
import org.davidmoten.rx.jdbc.pool.Pools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * @author fuchun
 */
public class AsyncDb {

  public static AsyncDb createDbInstance() {
    return Single.INSTANCE.create();
  }

  public Database createAsyncDbInstance() {

    val url = "jdbc:mysql://127.0.0.1:3306/demo";
    val cpuCores = Runtime.getRuntime().availableProcessors();
    return Database.nonBlocking()
        .url(url)
        .maxPoolSize(cpuCores)
        .healthCheck(DatabaseType.MYSQL)
        .user("test")
        .password("123")
        .maxIdleTime(5, TimeUnit.SECONDS)
        .idleTimeBeforeHealthCheck(5, TimeUnit.SECONDS)
        .connectionRetryInterval(5, TimeUnit.SECONDS)
        .build();
  }

  enum Single {
    INSTANCE;

    Single() {
      this.asyncDb = new AsyncDb();
    }

    AsyncDb create() {
      return this.asyncDb;
    }

    private AsyncDb asyncDb;
  }

  private AsyncDb() {

  }
}
