package br.com.danielwisky.extractor.service.vehicle.writer;

import br.com.danielwisky.extractor.domains.vehicle.GroupModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VersaoResource {

  private Long id;

  private String name;

  public VersaoResource(final GroupModel groupModel) {
    this.id = groupModel.getId();
    this.name = groupModel.getName();
  }
}