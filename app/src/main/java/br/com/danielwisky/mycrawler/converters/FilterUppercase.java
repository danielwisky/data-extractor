package br.com.danielwisky.mycrawler.converters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class FilterUppercase implements FilterConverter {

  @Override
  public String convert(final String value, final String[] args) {
    return StringUtils.upperCase(value);
  }
}