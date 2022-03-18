package br.com.danielwisky.mycrawler.gateways.inputs.http.resources.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class CrawlerRequest {

  @NotBlank
  private String objectType;
  @URL
  private String url;
}