package br.com.danielwisky.mycrawler.gateways.outputs.kafka;

import br.com.danielwisky.mycrawler.domains.Crawler;
import br.com.danielwisky.mycrawler.gateways.CrawlerAsyncGateway;
import br.com.danielwisky.mycrawler.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CrawlerAsyncGatewayImpl implements CrawlerAsyncGateway {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final JsonUtils jsonUtils;

  @Override
  public void send(final Crawler crawler) {
    kafkaTemplate.send("my-crawler.crawler-request", jsonUtils.toJson(crawler));
  }
}