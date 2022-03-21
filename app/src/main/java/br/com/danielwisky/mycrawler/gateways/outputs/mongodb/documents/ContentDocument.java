package br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents;

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
public class ContentDocument {

  private String type;
  private String path;
  private String query;
  private List<FieldDocument> fields;
  private ContentDocument children;

  public ContentDocument(final Content content) {
    this.type = content.getType();
    this.path = content.getPath();
    this.query = content.getQuery();
    this.fields = emptyIfNull(content.getFields())
        .stream()
        .map(FieldDocument::new)
        .collect(toList());
    this.children = ofNullable(content.getChildren())
        .map(ContentDocument::new)
        .orElse(null);
  }

  public Content toDomain() {
    return Content.builder()
        .type(this.type)
        .path(this.path)
        .query(this.query)
        .fields(emptyIfNull(this.fields)
            .stream()
            .map(FieldDocument::toDomain)
            .collect(toList()))
        .children(ofNullable(this.children)
            .map(ContentDocument::toDomain)
            .orElse(null))
        .build();
  }
}
