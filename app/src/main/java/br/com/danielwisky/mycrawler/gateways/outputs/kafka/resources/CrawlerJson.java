package br.com.danielwisky.mycrawler.gateways.outputs.kafka.resources;

import br.com.danielwisky.mycrawler.domains.Crawler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrawlerJson {

  private String id;
  private String type;
  private String url;

  public CrawlerJson(final Crawler crawler) {
    this.id = crawler.getId();
    this.type = crawler.getType();
    this.url = crawler.getUrl();
  }
}