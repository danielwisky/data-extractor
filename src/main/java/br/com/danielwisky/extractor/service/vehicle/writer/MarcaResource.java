package br.com.danielwisky.extractor.service.vehicle.writer;

import br.com.danielwisky.extractor.domains.vehicle.Brand;
import lombok.Data;

@Data
public class MarcaResource {

  private Long id;

  private String nome;

  public MarcaResource(final Brand brand) {
    this.id = brand.getId();
    this.nome = brand.getName();
  }
}
