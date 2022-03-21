package br.com.danielwisky.mycrawler.gateways;

import br.com.danielwisky.mycrawler.domains.Content;
import br.com.danielwisky.mycrawler.domains.CrawlerLine;
import java.util.List;
import java.util.Map;

public interface CrawlerLineExternalGateway {

  List<CrawlerLine> findBy(String url, Content content, Map<String, String> parameters);
}