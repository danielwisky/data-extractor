package br.com.danielwisky.mycrawler.gateways.outputs.kafka.resources;

import static java.util.Optional.ofNullable;

import br.com.danielwisky.mycrawler.domains.Content;
import br.com.danielwisky.mycrawler.domains.Crawler;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrawlerContentJson {

  private CrawlerJson crawler;
  private ContentJson content;
  private Map<String, String> parameters;

  public CrawlerContentJson(
      final Crawler crawler,
      final Content content,
      final Map<String, String> parameters) {
    this.crawler = ofNullable(crawler)
        .map(CrawlerJson::new)
        .orElse(null);
    this.content = ofNullable(content)
        .map(ContentJson::new)
        .orElse(null);
    this.parameters = parameters;
  }
}