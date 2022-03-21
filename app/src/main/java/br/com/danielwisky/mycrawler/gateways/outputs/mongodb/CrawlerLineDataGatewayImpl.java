package br.com.danielwisky.mycrawler.gateways.outputs.mongodb;

import br.com.danielwisky.mycrawler.domains.CrawlerLine;
import br.com.danielwisky.mycrawler.gateways.CrawlerLineDataGateway;
import br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents.CrawlerLineDocument;
import br.com.danielwisky.mycrawler.gateways.outputs.mongodb.repositories.CrawlerLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CrawlerLineDataGatewayImpl implements CrawlerLineDataGateway {

  private final CrawlerLineRepository crawlerLineRepository;

  @Override
  public CrawlerLine save(final CrawlerLine crawlerLine) {
    return crawlerLineRepository.save(new CrawlerLineDocument(crawlerLine)).toDomain();
  }
}