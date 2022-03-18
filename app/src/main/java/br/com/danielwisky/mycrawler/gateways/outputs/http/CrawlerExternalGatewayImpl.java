package br.com.danielwisky.mycrawler.gateways.outputs.http;

import br.com.danielwisky.mycrawler.gateways.CrawlerExternalGateway;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CrawlerExternalGatewayImpl implements CrawlerExternalGateway {

  @Override
  @Cacheable(value = "extract-html", keyGenerator = "customKeyGenerator")
  public String getContent(final String url) {
    try {
      return Jsoup.connect(url).get().html();
    } catch (IOException e) {
      log.error("Error extracting page content: {}", url, e);
      throw new RuntimeException(e.getMessage());
    }
  }
}