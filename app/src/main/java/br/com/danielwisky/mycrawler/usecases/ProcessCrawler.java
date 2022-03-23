package br.com.danielwisky.mycrawler.usecases;

import static java.util.Objects.nonNull;

import br.com.danielwisky.mycrawler.domains.Content;
import br.com.danielwisky.mycrawler.domains.Crawler;
import br.com.danielwisky.mycrawler.gateways.CrawlerAsyncGateway;
import br.com.danielwisky.mycrawler.gateways.CrawlerLineDataGateway;
import br.com.danielwisky.mycrawler.gateways.CrawlerLineExternalGateway;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessCrawler {

  private static final String PARENT_ID = "parent-id:hidden";

  private final CrawlerLineDataGateway crawlerLineDataGateway;
  private final CrawlerLineExternalGateway crawlerLineExternalGateway;
  private final CrawlerAsyncGateway crawlerAsyncGateway;

  public void execute(
      final Crawler crawler,
      final Content content,
      final Map<String, String> parameters) {

    crawlerLineExternalGateway.findBy(crawler.getUrl(), content, parameters)
        .forEach(line -> {
          line.setCrawlerId(crawler.getId());
          line.setParentId(getParentKey(parameters));
          line = crawlerLineDataGateway.save(line);
          if (nonNull(content.getChildren())) {
            parameters.put(PARENT_ID, line.getId());
            replicateParameters(content, parameters, line.getFields());
            crawlerAsyncGateway.send(crawler, content.getChildren(), parameters);
          }
        });
  }

  private String getParentKey(final Map<String, String> parameters) {
    return parameters.containsKey(PARENT_ID) ? parameters.get(PARENT_ID) : null;
  }

  private void replicateParameters(
      final Content content,
      final Map<String, String> parameters,
      final Map<String, String> parentParameters) {

    content.getFields()
        .stream()
        .filter(field -> BooleanUtils.isTrue(field.getReplicate()))
        .forEach(field -> {
          if (parentParameters.containsKey(field.getName())) {
            final String key = String.format("%s_%s", content.getType(), field.getName());
            final String value = parentParameters.get(field.getName());
            parameters.put(key, value);
          }
        });
  }
}