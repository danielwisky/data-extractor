package br.com.danielwisky.mycrawler.gateways.inputs.kafka.resources;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

import br.com.danielwisky.mycrawler.domains.Field;
import br.com.danielwisky.mycrawler.domains.enums.ValueConverterType;
import java.util.List;
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
  private Boolean replicate;

  public Field toDomain() {
    return Field.builder()
        .name(this.name)
        .converter(ofNullable(this.converter)
            .map(ValueConverterType::valueOf)
            .orElse(null))
        .filters(emptyIfNull(this.filters)
            .stream()
            .map(FilterJson::toDomain)
            .collect(toList()))
        .replicate(this.replicate)
        .build();
  }
}