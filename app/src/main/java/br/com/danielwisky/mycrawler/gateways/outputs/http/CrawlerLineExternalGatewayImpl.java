package br.com.danielwisky.mycrawler.gateways.outputs.http;

import static java.util.stream.Collectors.toList;

import br.com.danielwisky.mycrawler.converters.FilterConverter;
import br.com.danielwisky.mycrawler.converters.ValueConverter;
import br.com.danielwisky.mycrawler.domains.Content;
import br.com.danielwisky.mycrawler.domains.CrawlerLine;
import br.com.danielwisky.mycrawler.domains.Field;
import br.com.danielwisky.mycrawler.domains.Filter;
import br.com.danielwisky.mycrawler.gateways.BodyExternalGateway;
import br.com.danielwisky.mycrawler.gateways.CrawlerLineExternalGateway;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlerLineExternalGatewayImpl implements CrawlerLineExternalGateway {

  private final BodyExternalGateway bodyExternalGateway;
  private final BeanFactory beanFactory;

  @Override
  public List<CrawlerLine> findBy(final String url, final Content content) {

    final String html = bodyExternalGateway.getBody(
        String.format("%s%s", url, StringUtils.trimToEmpty(content.getPath())));
    final Elements select = Jsoup.parse(html).select(content.getQuery());

    return select.stream()
        .map(element -> buildLine(content, element))
        .collect(toList());
  }

  private CrawlerLine buildLine(final Content content, final Element element) {
    return CrawlerLine
        .builder()
        .type(content.getType())
        .fields(extractFields(element, content.getFields()))
        .build();
  }

  public Map<String, String> extractFields(final Element element, final List<Field> fields) {
    return fields
        .stream()
        .collect(Collectors.toMap(Field::getName, field -> converter(element, field)));
  }

  private String converter(final Element element, final Field field) {
    final ValueConverter converter =
        beanFactory.getBean(field.getConverter().getBean(), ValueConverter.class);
    return applyFilter(field.getFilters(), converter.convert(element));
  }

  private String applyFilter(final List<Filter> filters, String value) {
    for (Filter filter : filters) {
      final FilterConverter converter =
          beanFactory.getBean(filter.getConverter().getBean(), FilterConverter.class);
      value = converter.convert(value, filter.getParameters());
    }
    return value;
  }
}