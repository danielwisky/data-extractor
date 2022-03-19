package br.com.danielwisky.mycrawler.usecases;

import br.com.danielwisky.mycrawler.domains.Configuration;
import br.com.danielwisky.mycrawler.domains.Crawler;
import br.com.danielwisky.mycrawler.domains.exceptions.ResourceNotFoundException;
import br.com.danielwisky.mycrawler.gateways.ConfigurationDataGateway;
import br.com.danielwisky.mycrawler.gateways.CrawlerExternalGateway;
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

  public void execute(final Crawler crawler) {

    final Configuration configuration = configurationDataGateway
        .findByObjectTypeAndUrl(crawler.getObjectType(), crawler.getUrl())
        .orElseThrow(() -> new ResourceNotFoundException("Configuration not found."));

    final String html = crawlerExternalGateway.getContent(configuration.getUrl());
    final Document document = Jsoup.parse(html);

    Elements select = document.select(".jss2 a");

    log.info("Document: {}", document);

    

  }

}