package br.com.danielwisky.mycrawler.converters;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class FilterSubstringAfterLast implements FilterConverter {

  @Override
  public String convert(final String value, final String[] args) {
    return StringUtils.substringAfterLast(value, args[INTEGER_ZERO]);
  }
}