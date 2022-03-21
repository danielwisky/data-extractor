package br.com.danielwisky.mycrawler.gateways.inputs.kafka.resources;

import static java.util.Optional.ofNullable;

import br.com.danielwisky.mycrawler.domains.Filter;
import br.com.danielwisky.mycrawler.domains.enums.FilterConterterType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterJson {

  private String converter;
  private String[] parameters;

  public Filter toDomain() {
    return Filter.builder()
        .converter(ofNullable(this.converter)
            .map(FilterConterterType::valueOf)
            .orElse(null))
        .parameters(this.parameters)
        .build();
  }
}
