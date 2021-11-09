package br.com.danielwisky.extractor.service;

import br.com.danielwisky.extractor.domains.vehicle.Brand;
import java.util.List;
import lombok.SneakyThrows;
public interface VehicleExtractService {
  @SneakyThrows
  List<Brand> extract();
}
