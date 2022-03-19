package br.com.danielwisky.mycrawler.gateways;

import br.com.danielwisky.mycrawler.domains.CrawlerLine;

public interface CrawlerLineDataGateway {

  CrawlerLine save(CrawlerLine crawler);

}