package br.com.danielwisky.mycrawler.gateways.outputs.kafka.resources;

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

  public ContentJson(final Content content) {
    this.type = content.getType();
    this.path = content.getPath();
    this.query = content.getQuery();
    this.fields = emptyIfNull(content.getFields())
        .stream()
        .map(FieldJson::new)
        .collect(toList());
    this.children = ofNullable(content.getChildren())
        .map(ContentJson::new)
        .orElse(null);
  }
}