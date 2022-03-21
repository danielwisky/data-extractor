package br.com.danielwisky.mycrawler.gateways.outputs.kafka.resources;

import static java.util.Optional.ofNullable;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

import br.com.danielwisky.mycrawler.domains.Field;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldJson {

  private String name;
  private String converter;
  private List<FilterJson> filters;

  public FieldJson(final Field field) {
    this.name = field.getName();
    this.converter = ofNullable(field.getConverter())
        .map(Enum::name)
        .orElse(null);
    this.filters = emptyIfNull(field.getFilters())
        .stream()
        .map(FilterJson::new)
        .collect(Collectors.toList());
  }
}