package br.com.danielwisky.mycrawler.gateways.inputs.kafka;

import br.com.danielwisky.mycrawler.domains.Crawler;
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

  @KafkaListener(topics = "my-crawler.crawler-request")
  public void receive(final String message) {
    try {
      log.info("Receiving message: {}", message);
      final Crawler crawler = jsonUtils.toObject(message, Crawler.class);
      processCrawler.execute(crawler);
    } catch (Exception ex) {
      log.error("Error processing message: {}", message, ex);
    }
  }
}