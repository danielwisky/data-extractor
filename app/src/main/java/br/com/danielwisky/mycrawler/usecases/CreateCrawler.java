package br.com.danielwisky.mycrawler.usecases;

import static br.com.danielwisky.mycrawler.domains.CrawlerStatus.WAITING_PROCESSING;

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

  private final FindConfigurationsByObjectTypeAndUrl findConfigurationsByObjectTypeAndUrl;

  private final CrawlerDataGateway crawlerDataGateway;
  private final CrawlerAsyncGateway crawlerAsyncGateway;

  public List<Crawler> execute(final String objectType, final String url) {
    return findConfigurationsByObjectTypeAndUrl.execute(objectType, url)
        .stream()
        .map(this::saveAndSendToProcess)
        .collect(Collectors.toList());
  }

  private Crawler saveAndSendToProcess(final Configuration configuration) {
    final Crawler crawler = crawlerDataGateway.save(Crawler.builder()
        .url(configuration.getUrl())
        .objectType(configuration.getType())
        .status(WAITING_PROCESSING)
        .build());
    crawlerAsyncGateway.send(crawler);
    return crawler;
  }
}