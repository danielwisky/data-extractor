package br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents;

import br.com.danielwisky.mycrawler.domains.Configuration;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("configs")
@CompoundIndexes({
    @CompoundIndex(name = "objectType_url_idx", def = "{'objectType' : 1, 'url': 1}", unique = true)
})
public class ConfigurationDocument {

  @Id
  private String id;
  @Indexed
  private String objectType;
  private String url;

  @CreatedDate
  private LocalDateTime createdDate;
  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  public ConfigurationDocument(final Configuration configuration) {
    this.id = configuration.getId();
    this.objectType = configuration.getObjectType();
    this.url = configuration.getUrl();
    this.createdDate = configuration.getCreatedDate();
    this.lastModifiedDate = configuration.getLastModifiedDate();
  }

  public Configuration toDomain() {
    return Configuration.builder()
        .id(this.id)
        .objectType(this.objectType)
        .url(this.url)
        .createdDate(this.createdDate)
        .lastModifiedDate(this.lastModifiedDate)
        .build();
  }
}