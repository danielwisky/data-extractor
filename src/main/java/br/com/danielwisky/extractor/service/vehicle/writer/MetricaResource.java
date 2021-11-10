package br.com.danielwisky.extractor.service.vehicle.writer;

import br.com.danielwisky.extractor.domains.vehicle.Metric;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MetricaResource {

  private String tipo;

  private Double potencia;

  private Double torque;

  @JsonProperty("consumo_cidade")
  private Double consumoCidade;

  @JsonProperty("consumo_estrada")
  private Double consumoEstrada;

  public MetricaResource(final Metric metric) {
    this.tipo = metric.getType();
    this.torque = metric.getTorque();
    this.potencia = metric.getPower();
    this.consumoCidade = metric.getCity();
    this.consumoEstrada = metric.getRoad();
  }
}
