package br.com.danielwisky.mycrawler.gateways.inputs.kafka.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrawlerContentJson {

  private CrawlerJson crawler;
  private ContentJson content;
}