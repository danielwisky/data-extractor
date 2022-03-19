package br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents;

import static java.util.Optional.ofNullable;

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
    @CompoundIndex(name = "type_url_idx", def = "{'type' : 1, 'url': 1}", unique = true)
})
public class ConfigurationDocument {

  @Id
  private String id;
  @Indexed
  private String type;
  private String url;
  private ContentDocument content;

  @CreatedDate
  private LocalDateTime createdDate;
  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  public ConfigurationDocument(final Configuration configuration) {
    this.id = configuration.getId();
    this.type = configuration.getType();
    this.url = configuration.getUrl();
    this.content = ofNullable(configuration.getContent())
        .map(ContentDocument::new)
        .orElse(null);
    this.createdDate = configuration.getCreatedDate();
    this.lastModifiedDate = configuration.getLastModifiedDate();
  }

  public Configuration toDomain() {
    return Configuration.builder()
        .id(this.id)
        .type(this.type)
        .url(this.url)
        .content(ofNullable(this.content)
            .map(ContentDocument::toDomain)
            .orElse(null))
        .createdDate(this.createdDate)
        .lastModifiedDate(this.lastModifiedDate)
        .build();
  }
}