package br.com.danielwisky.mycrawler.gateways;

import br.com.danielwisky.mycrawler.domains.Content;
import br.com.danielwisky.mycrawler.domains.Crawler;
import java.util.Map;

public interface CrawlerAsyncGateway {

  void send(Crawler crawler, Content children, Map<String, String> parameters);
}
