package br.com.danielwisky.mycrawler.converters;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class ValueFromText implements ValueConverter {

  @Override
  public String convert(final Element element) {
    return StringUtils.trim(element.text());
  }
}