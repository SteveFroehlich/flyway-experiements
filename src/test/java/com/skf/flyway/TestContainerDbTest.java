package com.skf.flyway;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.migration.JavaMigration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.*;
import java.util.Properties;

public class TestContainerDbTest {
    /*
        Resources:
            Test container postgres info:
                https://www.testcontainers.org/modules/databases/postgres/

            Flyway info:
                https://flywaydb.org/documentation/getstarted/firststeps/maven
     */
  private static final String POSTGRES_VERSION = "postgres:14-alpine";
  private String jdbcUrl;
  private Flyway flyway;

  @Rule
  public PostgreSQLContainer database = new PostgreSQLContainer<>(POSTGRES_VERSION)
      .withDatabaseName("test-database")
      .withUsername("sa")
      .withPassword("sa");

  @Before
  public void setUp() {
    JavaMigration migration;
    jdbcUrl = database.getJdbcUrl();
  }

  @Test
  public void basicTest() throws SQLException {
    flyway = Flyway.configure()
        .dataSource(jdbcUrl, "sa", "sa")
        .load();
    flyway.migrate();

    Connection conn = getConnection();
    insertData(conn);
  }

  @Test
  public void migrationTest() throws SQLException {
    // given initial migration to version 2
    String databaseVersion = "2";
    flyway = Flyway.configure()
        .target(databaseVersion)
        .dataSource(jdbcUrl, "sa", "sa")
        .load();
    flyway.migrate();

    // and some data is printed
    Connection conn = getConnection();
    insertData(conn);

    // when the version is updated
    databaseVersion = "4";

    // then the migrations changing the db should succeed
    flyway = Flyway.configure()
        .target(databaseVersion)
        .dataSource(jdbcUrl, "sa", "sa")
        .load();
    flyway.migrate();
    // and print the db contents
    insertData(conn);
  }

  private Connection getConnection() throws SQLException {
    Properties props = new Properties();
    props.setProperty("user", "sa");
    props.setProperty("password", "sa");
    props.setProperty("ssl", "false");
    return DriverManager.getConnection(jdbcUrl, props);
  }

  private void insertData(Connection conn) {
    String select = "select * from PERSON";
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(select);
      while (rs.next()) {
        int id = rs.getInt("ID");
        String name = rs.getString("NAME");
        System.out.println("id=" + id + ", name=" + name);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
