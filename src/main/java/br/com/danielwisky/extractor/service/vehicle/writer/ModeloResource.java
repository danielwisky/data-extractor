package br.com.danielwisky.extractor.service.vehicle.writer;

import br.com.danielwisky.extractor.domains.vehicle.Model;
import lombok.Data;

@Data
public class ModeloResource {

  private Long id;

  private String nome;

  public ModeloResource(final Model model) {
    this.id = model.getId();
    this.nome = model.getName();
  }
}