package br.com.danielwisky.mycrawler.gateways.outputs.kafka;

import static br.com.danielwisky.mycrawler.domains.constants.Topic.CRAWLER_REQUEST;

import br.com.danielwisky.mycrawler.domains.Content;
import br.com.danielwisky.mycrawler.domains.Crawler;
import br.com.danielwisky.mycrawler.gateways.CrawlerAsyncGateway;
import br.com.danielwisky.mycrawler.gateways.outputs.kafka.resources.CrawlerContentJson;
import br.com.danielwisky.mycrawler.utils.JsonUtils;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CrawlerAsyncGatewayImpl implements CrawlerAsyncGateway {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final JsonUtils jsonUtils;

  @Override
  public void send(
      final Crawler crawler, final Content content, final Map<String, String> parameters) {
    final String message = jsonUtils.toJson(new CrawlerContentJson(crawler, content, parameters));
    kafkaTemplate.send(CRAWLER_REQUEST, message);
  }
}