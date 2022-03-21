package br.com.danielwisky.mycrawler.gateways;

import br.com.danielwisky.mycrawler.domains.Content;
import br.com.danielwisky.mycrawler.domains.CrawlerLine;
import java.util.List;

public interface CrawlerLineExternalGateway {

  List<CrawlerLine> findBy(String url, Content content);
}