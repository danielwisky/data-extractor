package br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents;

import static java.util.Optional.ofNullable;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

import br.com.danielwisky.mycrawler.domains.Field;
import br.com.danielwisky.mycrawler.domains.enums.ValueConverterType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldDocument {

  private String name;
  private String converter;
  private List<FilterDocument> filters;
  private Boolean replicate;

  public FieldDocument(final Field field) {
    this.name = field.getName();
    this.converter = ofNullable(field.getConverter())
        .map(Enum::name)
        .orElse(null);
    this.filters = emptyIfNull(field.getFilters())
        .stream()
        .map(FilterDocument::new)
        .collect(Collectors.toList());
    this.replicate = field.getReplicate();
  }

  public Field toDomain() {
    return Field.builder()
        .name(this.name)
        .converter(ofNullable(this.converter)
            .map(ValueConverterType::valueOf)
            .orElse(null))
        .filters(emptyIfNull(this.filters)
            .stream()
            .map(FilterDocument::toDomain)
            .collect(Collectors.toList()))
        .replicate(this.replicate)
        .build();
  }
}