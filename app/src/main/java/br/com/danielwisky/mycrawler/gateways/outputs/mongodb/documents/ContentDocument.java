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

  private String key;
  private String keyQuery;
  private List<FieldDocument> fields;

  private String childrenPath;
  private String childrenQuery;
  private ContentDocument children;

  public ContentDocument(final Content content) {
    this.key = content.getKey();
    this.keyQuery = content.getKeyQuery();
    this.fields = emptyIfNull(content.getFields())
        .stream()
        .map(FieldDocument::new)
        .collect(toList());
    this.childrenPath = content.getChildrenPath();
    this.childrenQuery = content.getChildrenQuery();
    this.children = ofNullable(content.getChildren())
        .map(ContentDocument::new)
        .orElse(null);
  }

  public Content toDomain() {
    return Content.builder()
        .key(this.key)
        .keyQuery(this.keyQuery)
        .fields(emptyIfNull(this.fields)
            .stream()
            .map(FieldDocument::toDomain)
            .collect(toList()))
        .childrenPath(this.childrenPath)
        .childrenQuery(this.childrenQuery)
        .children(ofNullable(this.children)
            .map(ContentDocument::toDomain)
            .orElse(null))
        .build();
  }
}
