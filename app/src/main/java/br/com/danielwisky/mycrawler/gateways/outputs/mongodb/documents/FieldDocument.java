package br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents;

import br.com.danielwisky.mycrawler.domains.Field;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldDocument {

  private String name;
  private String query;
  private List<String> filters;

  public FieldDocument(final Field field) {
    this.name = field.getName();
    this.query = field.getQuery();
    this.filters = field.getFilters();

  }

  public Field toDomain() {
    return Field.builder()
        .name(this.name)
        .query(this.query)
        .filters(this.filters)
        .build();
  }
}