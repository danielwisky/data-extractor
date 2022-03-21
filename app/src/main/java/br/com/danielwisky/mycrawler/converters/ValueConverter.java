package br.com.danielwisky.mycrawler.converters;

import org.jsoup.nodes.Element;

public interface ValueConverter {

  String convert(Element element);

}