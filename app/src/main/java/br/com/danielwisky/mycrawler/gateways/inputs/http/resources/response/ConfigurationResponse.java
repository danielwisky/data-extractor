package br.com.danielwisky.mycrawler.gateways.inputs.http.resources.response;

import br.com.danielwisky.mycrawler.domains.Configuration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationResponse {

  private String id;
  private String url;

  public ConfigurationResponse(final Configuration configuration) {
    this.id = configuration.getId();
    this.url = configuration.getUrl();
  }
}
