package br.com.danielwisky.mycrawler.configs.cache;

import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;

public class CustomKeyGenerator implements KeyGenerator {

  private static final String DELIMITER = "_";

  public Object generate(final Object target, final Method method, final Object... params) {
    return target.getClass().getSimpleName()
        + DELIMITER
        + method.getName()
        + DELIMITER
        + StringUtils.join(params, DELIMITER);
  }
}