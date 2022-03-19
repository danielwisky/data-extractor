package br.com.danielwisky.mycrawler.domains;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content {

  private String path;
  private String query;
  private List<Field> fields;

  private Content children;
}