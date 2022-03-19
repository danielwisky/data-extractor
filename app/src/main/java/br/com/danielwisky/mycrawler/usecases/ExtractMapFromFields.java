package br.com.danielwisky.mycrawler.usecases;

import br.com.danielwisky.mycrawler.domains.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class ExtractMapFromFields {

  public Map<String, String> execute(final Element element, final List<Field> fields) {
    return fields
        .stream()
        .collect(Collectors.toMap(Field::getName, field -> extractValue(element, field)));
  }

  private String extractValue(final Element element, final Field field) {
    //if (HREF.equals(field.getProperty())) {
    //  return element.attr(HREF.name());
    //} else if (TEXT.equals(field.getProperty())) {
    //  return element.text();
    //} else {
    //  return null;
    //}
    return null;
  }
}
