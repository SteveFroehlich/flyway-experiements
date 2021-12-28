package com.skf.flyway;

import org.flywaydb.core.Flyway;
import org.junit.Test;

import java.sql.SQLException;

public class ExternalDbTest {
  /*
      Test setup:
        $ docker image ls
        if NO postgres 14-alpine image run
          $ docker pull postgres:14-alpine
        then run:
          $ ./run-postgres.sh
        if there is a postgres image:
          $ docker container start postgres13
        Create db:
          $ run-create-db.sh
        Run this test via IntelliJ
   */
  private String jdbcUrl = "jdbc:postgresql://localhost:5432/testdb";
  private Flyway flyway;

  @Test
  public void test() throws SQLException {
      flyway = Flyway.configure()
          .dataSource(jdbcUrl, "postgres", "sa")
          .load();
      flyway.migrate();

    }
}
