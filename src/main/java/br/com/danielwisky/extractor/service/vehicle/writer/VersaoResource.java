package br.com.danielwisky.extractor.service.vehicle.writer;

import br.com.danielwisky.extractor.domains.vehicle.GroupModel;
import lombok.Data;

@Data
public class VersaoResource {

  private Long id;

  private String nome;

  public VersaoResource(final GroupModel groupModel) {
    this.id = groupModel.getId();
    this.nome = groupModel.getName();
  }
}