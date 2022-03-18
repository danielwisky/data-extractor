package br.com.danielwisky.mycrawler.domains;

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
  private CrawlerStatus status;
}