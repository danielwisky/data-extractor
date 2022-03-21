package br.com.danielwisky.mycrawler.gateways;

import br.com.danielwisky.mycrawler.domains.Content;
import br.com.danielwisky.mycrawler.domains.Crawler;

public interface CrawlerAsyncGateway {

  void send(Crawler crawler, Content children);
}
