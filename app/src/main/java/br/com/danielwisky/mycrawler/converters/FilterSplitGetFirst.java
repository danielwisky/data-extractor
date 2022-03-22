package br.com.danielwisky.mycrawler.converters;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class FilterSplitGetFirst implements FilterConverter {

  @Override
  public String convert(final String value, final String[] args) {
    return StringUtils.trimToEmpty(value).split(args[INTEGER_ZERO])[INTEGER_ZERO];
  }
}