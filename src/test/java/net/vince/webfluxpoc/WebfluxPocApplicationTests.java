package net.vince.webfluxpoc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
class WebfluxPocApplicationTests {

  @Container
  @SuppressWarnings("rawtypes")
  public PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:12-alpine")).withDatabaseName("WEBFLUX_POC")
                                                                                                            .withUsername("admin")
                                                                                                            .withPassword("postgres");

  @Test
  void contextLoads() {
  }

}
