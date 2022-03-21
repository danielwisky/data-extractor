package br.com.danielwisky.mycrawler.gateways.inputs.kafka.resources;

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
}