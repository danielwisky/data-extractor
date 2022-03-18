package br.com.danielwisky.mycrawler.gateways.inputs.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlerListener {

  @KafkaListener(topics = "my-crawler.crawler-request")
  public void receive(final String message) {

  }
}