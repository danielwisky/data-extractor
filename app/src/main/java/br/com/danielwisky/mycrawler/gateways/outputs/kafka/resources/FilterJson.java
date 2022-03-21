package br.com.danielwisky.mycrawler.gateways.outputs.kafka.resources;

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

  }
}
