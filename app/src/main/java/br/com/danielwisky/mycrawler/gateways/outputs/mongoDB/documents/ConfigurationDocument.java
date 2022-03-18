package br.com.danielwisky.mycrawler.gateways.outputs.mongoDB.documents;

import br.com.danielwisky.mycrawler.domains.Configuration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("configs")
public class ConfigurationDocument {

  @Id
  private String id;
  private String url;

  public ConfigurationDocument(final Configuration configuration) {
    this.id = configuration.getId();
    this.url = configuration.getUrl();
  }

  public Configuration toDomain() {
    return Configuration.builder()
        .id(this.id)
        .url(this.url)
        .build();
  }
}