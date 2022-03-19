package br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents;

import static java.util.Optional.ofNullable;

import br.com.danielwisky.mycrawler.domains.Crawler;
import br.com.danielwisky.mycrawler.domains.CrawlerStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("crawlers")
public class CrawlerDocument {

  @Id
  private String id;
  private String type;
  private String url;
  private String status;

  @CreatedDate
  private LocalDateTime createdDate;
  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  public CrawlerDocument(final Crawler crawler) {
    this.id = crawler.getId();
    this.type = crawler.getType();
    this.url = crawler.getUrl();
    this.status = ofNullable(crawler.getStatus())
        .map(Enum::name)
        .orElse(null);
    this.createdDate = crawler.getCreatedDate();
    this.lastModifiedDate = crawler.getLastModifiedDate();
  }

  public Crawler toDomain() {
    return Crawler.builder()
        .id(this.id)
        .type(this.type)
        .url(this.url)
        .status(ofNullable(this.status)
            .map(CrawlerStatus::valueOf)
            .orElse(null))
        .createdDate(this.createdDate)
        .lastModifiedDate(this.lastModifiedDate)
        .build();
  }
}