package br.com.danielwisky.mycrawler.converters;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class FilterReplaceToEmpty implements FilterConverter {

  @Override
  public String convert(String value, String[] args) {
    return Arrays.stream(args)
        .reduce(value, (str, toRem) -> str.replaceAll(toRem, EMPTY));
  }
}
