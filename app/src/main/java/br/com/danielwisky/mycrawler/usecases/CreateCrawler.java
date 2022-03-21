package br.com.danielwisky.mycrawler.usecases;

import static br.com.danielwisky.mycrawler.domains.enums.CrawlerStatus.WAITING_PROCESSING;

import br.com.danielwisky.mycrawler.domains.Configuration;
import br.com.danielwisky.mycrawler.domains.Crawler;
import br.com.danielwisky.mycrawler.gateways.CrawlerAsyncGateway;
import br.com.danielwisky.mycrawler.gateways.CrawlerDataGateway;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCrawler {

  private final FindConfigurationsByTypeAndUrl findConfigurationsByTypeAndUrl;

  private final CrawlerDataGateway crawlerDataGateway;
  private final CrawlerAsyncGateway crawlerAsyncGateway;

  public List<Crawler> execute(final String type, final String url) {
    return findConfigurationsByTypeAndUrl.execute(type, url)
        .stream()
        .map(this::saveAndSendToProcess)
        .collect(Collectors.toList());
  }

  private Crawler saveAndSendToProcess(final Configuration configuration) {
    final Crawler crawler = crawlerDataGateway.save(Crawler.builder()
        .url(configuration.getUrl())
        .type(configuration.getType())
        .status(WAITING_PROCESSING)
        .build());
    crawlerAsyncGateway.send(crawler, configuration.getContent());
    return crawler;
  }
}