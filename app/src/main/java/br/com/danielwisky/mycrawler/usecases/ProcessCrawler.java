package br.com.danielwisky.mycrawler.usecases;

import static java.util.Objects.nonNull;

import br.com.danielwisky.mycrawler.domains.Content;
import br.com.danielwisky.mycrawler.domains.Crawler;
import br.com.danielwisky.mycrawler.gateways.CrawlerAsyncGateway;
import br.com.danielwisky.mycrawler.gateways.CrawlerLineDataGateway;
import br.com.danielwisky.mycrawler.gateways.CrawlerLineExternalGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessCrawler {

  private final CrawlerLineDataGateway crawlerLineDataGateway;
  private final CrawlerLineExternalGateway crawlerLineExternalGateway;
  private final CrawlerAsyncGateway crawlerAsyncGateway;

  public void execute(final Crawler crawler, final Content content) {
    crawlerLineExternalGateway.findBy(crawler.getUrl(), content)
        .forEach(line -> {
          line.setCrawlerId(crawler.getId());
          crawlerLineDataGateway.save(line);
          if (nonNull(content.getChildren())) {
            crawlerAsyncGateway.send(crawler, content.getChildren());
          }
        });
  }
}