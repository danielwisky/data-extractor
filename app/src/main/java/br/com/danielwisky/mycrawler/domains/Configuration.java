package br.com.danielwisky.mycrawler.domains;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Configuration implements Serializable {

  private String id;
  private String type;
  private String url;

  private String contentPath;
  private String contentQuery;
  private Content content;

  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;
}