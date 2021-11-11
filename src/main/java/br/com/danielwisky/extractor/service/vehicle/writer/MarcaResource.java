package br.com.danielwisky.extractor.service.vehicle.writer;

import br.com.danielwisky.extractor.domains.vehicle.Brand;
import lombok.Data;

@Data
public class MarcaResource {

  private Long id;

  private String name;

  public MarcaResource(final Brand brand) {
    this.id = brand.getId();
    this.name = brand.getName();
  }
}
