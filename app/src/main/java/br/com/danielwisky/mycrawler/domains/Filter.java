package br.com.danielwisky.mycrawler.domains;

import br.com.danielwisky.mycrawler.domains.enums.FilterConterterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Filter {

  private FilterConterterType converter;
  private String[] parameters;
}