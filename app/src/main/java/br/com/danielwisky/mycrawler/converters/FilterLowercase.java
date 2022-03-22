package br.com.danielwisky.mycrawler.converters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class FilterLowercase implements FilterConverter {

  @Override
  public String convert(final String value, final String[] args) {
    return StringUtils.lowerCase(value);
  }
}