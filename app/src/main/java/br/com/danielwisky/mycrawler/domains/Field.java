package br.com.danielwisky.mycrawler.domains;

import br.com.danielwisky.mycrawler.domains.enums.ValueConverterType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Field {

  private String name;
  private ValueConverterType converter;
  private List<Filter> filters;
}