package ar.com.mercadolibre.ipgeolocation.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@TestConfiguration
public class TestRedisConfiguration {
  private RedisServer redisServer;

  @Value("${spring.data.redis.port}")
  private int redisPort;

  public TestRedisConfiguration() {
  }

  @PostConstruct
  public void postConstruct() {
    if (this.redisServer == null || !this.redisServer.isActive()) {
      redisServer = RedisServer.builder()
              .port(redisPort)
              .build();
      redisServer.start();
    }
  }

  @PreDestroy
  public void preDestroy() {
    if (this.redisServer != null) {
      this.redisServer.stop();
      this.redisServer = null;
    }
  }

}
