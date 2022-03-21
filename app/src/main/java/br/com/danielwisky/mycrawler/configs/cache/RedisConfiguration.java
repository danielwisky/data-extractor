package br.com.danielwisky.mycrawler.configs.cache;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

@EnableCaching
@Configuration
public class RedisConfiguration extends CachingConfigurerSupport {

  @Value("${spring.application.name}")
  private String applicationName;

  @Value("${spring.redis.ttlInMinutes}")
  private Integer ttlInMinutes;

  @Bean
  public RedisCacheConfiguration redisCacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(ttlInMinutes))
        .computePrefixWith(cacheName -> String.format("%s::%s::", applicationName, cacheName))
        .disableCachingNullValues();
  }

  @Bean("customKeyGenerator")
  public KeyGenerator keyGenerator() {
    return new CustomKeyGenerator();
  }
}