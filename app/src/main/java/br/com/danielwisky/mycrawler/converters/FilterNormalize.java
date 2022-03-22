package br.com.danielwisky.mycrawler.converters;

import java.text.Normalizer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class FilterNormalize implements FilterConverter {

  @Override
  public String convert(final String value, final String[] args) {
    return Normalizer
        .normalize(value, Normalizer.Form.NFD)
        .replaceAll("[^\\p{ASCII}]", StringUtils.EMPTY);
  }
}