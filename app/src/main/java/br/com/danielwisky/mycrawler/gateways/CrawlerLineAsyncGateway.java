package br.com.danielwisky.mycrawler.gateways;

import br.com.danielwisky.mycrawler.domains.Crawler;

public interface CrawlerLineAsyncGateway {

  void send(Crawler crawler);
}
