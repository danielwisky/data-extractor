package br.com.danielwisky.extractor.service.vehicle.writer;

import br.com.danielwisky.extractor.domains.vehicle.Vehicle;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class VehicleResource {

  private Long id;

  private String ano;

  private String motor;

  @JsonProperty("velocidade_maxima")
  private Double velocidadeMax;

  @JsonProperty("tempo")
  private Double tempo0a100;

  private Double peso;

  private List<MetricaResource> metricas;

  public VehicleResource(final Vehicle vehicle) {
    this.id = vehicle.getId();
    this.ano = vehicle.getYear();
    this.motor = vehicle.getEngine();
    this.peso = vehicle.getWeight();
    this.velocidadeMax = vehicle.getSpeed();
    this.tempo0a100 = vehicle.getTime0to100();
    this.metricas = vehicle.getMetrics().stream().map(MetricaResource::new).collect(Collectors.toList());
  }
}
