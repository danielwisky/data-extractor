package br.com.danielwisky.mycrawler.converters;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class ValueFromHref implements ValueConverter {

  @Override
  public String convert(final Element element) {
    return element.attr("href");
  }
}