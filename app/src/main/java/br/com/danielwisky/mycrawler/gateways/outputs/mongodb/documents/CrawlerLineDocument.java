package br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents;

import br.com.danielwisky.mycrawler.domains.CrawlerLine;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("crawlersLine")
public class CrawlerLineDocument {

  @Id
  private String id;
  @Indexed
  private String crawlerId;
  private Map<String, String> fields;
  @CreatedDate
  private LocalDateTime createdDate;
  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  public CrawlerLineDocument(final CrawlerLine crawlerLine) {
    this.id = crawlerLine.getId();
    this.crawlerId = crawlerLine.getCrawlerId();
    this.fields = crawlerLine.getFields();
    this.createdDate = crawlerLine.getCreatedDate();
    this.lastModifiedDate = crawlerLine.getLastModifiedDate();
  }

  public CrawlerLine toDomain() {
    return CrawlerLine.builder()
        .id(this.id)
        .crawlerId(this.crawlerId)
        .fields(this.fields)
        .createdDate(this.createdDate)
        .lastModifiedDate(this.lastModifiedDate)
        .build();
  }
}