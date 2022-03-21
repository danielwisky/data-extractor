package br.com.danielwisky.mycrawler.gateways.outputs.kafka.resources;

import static java.util.Optional.ofNullable;

import br.com.danielwisky.mycrawler.domains.Filter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterJson {

  private String converter;
  private String[] parameters;

  public FilterJson(final Filter filter) {
    this.converter = ofNullable(filter.getConverter())
        .map(Enum::name)
        .orElse(null);
    this.parameters = filter.getParameters();
  }
}
