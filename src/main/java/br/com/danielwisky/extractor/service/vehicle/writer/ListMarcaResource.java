package br.com.danielwisky.extractor.service.vehicle.writer;

import br.com.danielwisky.extractor.domains.vehicle.Brand;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class ListMarcaResource {

  private List<MarcaResource> marcas;

  public ListMarcaResource(final List<Brand> brands) {
    this.marcas = brands.stream().map(MarcaResource::new).collect(Collectors.toList());
  }
}