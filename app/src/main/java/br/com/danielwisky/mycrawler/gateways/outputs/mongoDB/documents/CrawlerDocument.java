package br.com.danielwisky.mycrawler.gateways.outputs.mongoDB.documents;

import static java.util.Optional.ofNullable;

import br.com.danielwisky.mycrawler.domains.Crawler;
import br.com.danielwisky.mycrawler.domains.CrawlerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("crawlers")
public class CrawlerDocument {

  @Id
  private String id;
  private String status;

  public CrawlerDocument(final Crawler crawler) {
    this.id = crawler.getId();
    this.status = ofNullable(crawler.getStatus())
        .map(Enum::name)
        .orElse(null);
  }

  public Crawler toDomain() {
    return Crawler.builder()
        .id(this.id)
        .status(ofNullable(this.status)
            .map(CrawlerStatus::valueOf)
            .orElse(null))
        .build();
  }
}