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

  private String key;
  private String keyQuery;
  private List<Field> fields;

  private String childrenPath;
  private String childrenQuery;
  private Content children;
}