package br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents;

import static java.util.Optional.ofNullable;

import br.com.danielwisky.mycrawler.domains.Filter;
import br.com.danielwisky.mycrawler.domains.enums.FilterConterterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterDocument {

  private String converter;
  private String[] parameters;

  public FilterDocument(final Filter filter) {
    this.converter = ofNullable(filter.getConverter())
        .map(Enum::name)
        .orElse(null);
    this.parameters = filter.getParameters();
  }

  public Filter toDomain() {
    return Filter.builder()
        .converter(ofNullable(this.converter)
            .map(FilterConterterType::valueOf)
            .orElse(null))
        .parameters(this.parameters)
        .build();
  }
}