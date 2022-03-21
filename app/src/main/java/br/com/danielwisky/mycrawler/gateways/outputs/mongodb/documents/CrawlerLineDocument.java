package br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents;

import static java.util.Optional.ofNullable;

import br.com.danielwisky.mycrawler.domains.CrawlerLine;
import br.com.danielwisky.mycrawler.domains.enums.CrawlerLineStatus;
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
@Document("crawlerLines")
public class CrawlerLineDocument {

  @Id
  private String id;
  @Indexed
  private String crawlerId;
  private String type;
  private Map<String, String> fields;
  private String status;
  @CreatedDate
  private LocalDateTime createdDate;
  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  public CrawlerLineDocument(final CrawlerLine crawlerLine) {
    this.id = crawlerLine.getId();
    this.crawlerId = crawlerLine.getCrawlerId();
    this.type = crawlerLine.getType();
    this.fields = crawlerLine.getFields();
    this.status = ofNullable(crawlerLine.getStatus())
        .map(Enum::name)
        .orElse(null);
    this.createdDate = crawlerLine.getCreatedDate();
    this.lastModifiedDate = crawlerLine.getLastModifiedDate();
  }

  public CrawlerLine toDomain() {
    return CrawlerLine.builder()
        .id(this.id)
        .crawlerId(this.crawlerId)
        .type(this.type)
        .fields(this.fields)
        .status(ofNullable(this.status)
            .map(CrawlerLineStatus::valueOf)
            .orElse(null))
        .createdDate(this.createdDate)
        .lastModifiedDate(this.lastModifiedDate)
        .build();
  }
}