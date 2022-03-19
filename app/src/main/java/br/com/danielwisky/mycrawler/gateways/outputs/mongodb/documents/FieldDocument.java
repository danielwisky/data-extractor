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
  private String value;
  private List<String> converters;

  public FieldDocument(final Field field) {
    this.name = field.getName();
    this.value = field.getValue();
    this.converters = field.getConverters();
  }

  public Field toDomain() {
    return Field.builder()
        .name(this.name)
        .value(this.value)
        .converters(this.converters)
        .build();
  }
}