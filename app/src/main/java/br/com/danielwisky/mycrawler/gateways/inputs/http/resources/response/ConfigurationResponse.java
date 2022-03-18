package br.com.danielwisky.mycrawler.gateways.inputs.http.resources.response;

import br.com.danielwisky.mycrawler.domains.Configuration;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class ConfigurationResponse {

  private String id;
  private String objectType;
  private String url;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;

  public ConfigurationResponse(final Configuration configuration) {
    this.id = configuration.getId();
    this.objectType = configuration.getObjectType();
    this.url = configuration.getUrl();
    this.createdDate = configuration.getCreatedDate();
    this.lastModifiedDate = configuration.getLastModifiedDate();
  }
}