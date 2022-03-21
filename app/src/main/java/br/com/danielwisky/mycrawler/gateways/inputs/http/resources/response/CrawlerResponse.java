package br.com.danielwisky.mycrawler.gateways.inputs.http.resources.response;

import static java.util.Optional.ofNullable;

import br.com.danielwisky.mycrawler.domains.Crawler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class CrawlerResponse {

  private String id;
  private String status;

  public CrawlerResponse(final Crawler crawler) {
    this.id = crawler.getId();
    this.status = ofNullable(crawler.getStatus())
        .map(Enum::name)
        .orElse(null);
  }
}
