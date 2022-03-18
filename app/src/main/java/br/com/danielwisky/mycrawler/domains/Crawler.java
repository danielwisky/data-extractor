package br.com.danielwisky.mycrawler.domains;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Crawler {

  private String id;
  private String objectType;
  private String url;

  private CrawlerStatus status;

  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;
}