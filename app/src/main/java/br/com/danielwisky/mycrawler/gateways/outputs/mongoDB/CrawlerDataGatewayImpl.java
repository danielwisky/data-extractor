package br.com.danielwisky.mycrawler.gateways.outputs.mongoDB;

import br.com.danielwisky.mycrawler.domains.Crawler;
import br.com.danielwisky.mycrawler.gateways.CrawlerDataGateway;
import br.com.danielwisky.mycrawler.gateways.outputs.mongoDB.documents.CrawlerDocument;
import br.com.danielwisky.mycrawler.gateways.outputs.mongoDB.repositories.CrawlerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CrawlerDataGatewayImpl implements CrawlerDataGateway {

  private final CrawlerRepository crawlerRepository;

  @Override
  public Crawler save(final Crawler crawler) {
    return crawlerRepository.save(new CrawlerDocument(crawler)).toDomain();
  }
}