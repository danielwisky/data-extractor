package br.com.danielwisky.mycrawler.gateways.inputs.kafka.resources;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

import br.com.danielwisky.mycrawler.domains.Content;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentJson {

  private String type;
  private String path;
  private String query;
  private List<FieldJson> fields;
  private ContentJson children;

  public Content toDomain() {
    return Content.builder()
        .type(this.type)
        .path(this.path)
        .query(this.query)
        .fields(emptyIfNull(this.fields)
            .stream()
            .map(FieldJson::toDomain)
            .collect(toList()))
        .children(ofNullable(this.children)
            .map(ContentJson::toDomain)
            .orElse(null))
        .build();
  }
}