package br.com.danielwisky.mycrawler.domains;

import br.com.danielwisky.mycrawler.domains.enums.CrawlerLineStatus;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlerLine {

  private String id;
  private String crawlerId;
  private String parentId;
  private String type;
  private Map<String, String> fields;
  private CrawlerLineStatus status;

  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;
}