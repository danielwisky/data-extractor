package br.com.danielwisky.extractor.service.vehicle.writer;

import br.com.danielwisky.extractor.domains.vehicle.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModeloResource {

  private Long id;

  private String name;

  public ModeloResource(final Model model) {
    this.id = model.getId();
    this.name = model.getName();
  }
}