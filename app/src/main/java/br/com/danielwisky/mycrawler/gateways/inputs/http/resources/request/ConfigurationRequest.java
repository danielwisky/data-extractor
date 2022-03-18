package br.com.danielwisky.mycrawler.gateways.inputs.http.resources.request;

import br.com.danielwisky.mycrawler.domains.Configuration;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationRequest {

  @URL
  @NotBlank
  private String url;

  public Configuration toDomain() {
    return Configuration.builder()
        .url(this.url)
        .build();
  }
}