package br.com.danielwisky.mycrawler.usecases;

import br.com.danielwisky.mycrawler.domains.Configuration;
import br.com.danielwisky.mycrawler.domains.Content;
import br.com.danielwisky.mycrawler.domains.Crawler;
import br.com.danielwisky.mycrawler.domains.exceptions.ResourceNotFoundException;
import br.com.danielwisky.mycrawler.gateways.ConfigurationDataGateway;
import br.com.danielwisky.mycrawler.gateways.CrawlerExternalGateway;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessCrawler {

  private final ConfigurationDataGateway configurationDataGateway;
  private final CrawlerExternalGateway crawlerExternalGateway;

  private final ExtractMapFromFields extractMapFromFields;

  public void execute(final Crawler crawler) {

    final Configuration configuration = configurationDataGateway
        .findByTypeAndUrl(crawler.getType(), crawler.getUrl())
        .orElseThrow(() -> new ResourceNotFoundException("Configuration not found."));

    Content content = configuration.getContent();

    final String html = crawlerExternalGateway.getContent(
        String.format("%s%s", configuration.getUrl(), content.getPath()));

    final Document document = Jsoup.parse(html);
    Elements select = document.select(content.getQuery());

    select.stream().forEach(element -> {
      Map<String, String> execute = extractMapFromFields.execute(element, content.getFields());
      log.info("map: {}", execute);
    });

    //log.info("Document: {}", document);


  }
}