package br.com.danielwisky.mycrawler.gateways.inputs.kafka;

import static br.com.danielwisky.mycrawler.domains.constants.Topic.CRAWLER_REQUEST;

import br.com.danielwisky.mycrawler.domains.Crawler;
import br.com.danielwisky.mycrawler.gateways.inputs.kafka.resources.CrawlerJson;
import br.com.danielwisky.mycrawler.gateways.outputs.kafka.resources.CrawlerContentJson;
import br.com.danielwisky.mycrawler.usecases.ProcessCrawler;
import br.com.danielwisky.mycrawler.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlerListener {

  private final ProcessCrawler processCrawler;
  private final JsonUtils jsonUtils;

  @KafkaListener(topics = CRAWLER_REQUEST)
  public void receive(final String message) {
    try {
      log.info("Receiving message: {}", message);
      final Crawler crawler = jsonUtils.toObject(message, CrawlerContentJson.class).toDomain();
      processCrawler.execute(crawler);
    } catch (Exception ex) {
      log.error("Error processing message: {}", message, ex);
    }
  }
}