package br.com.danielwisky.mycrawler.gateways.inputs.kafka.resources;

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

  public Crawler toDomain() {
    return Crawler.builder()
        .id(this.id)
        .type(this.type)
        .url(this.url)
        .build();
  }
}