package br.com.danielwisky.extractor.service.vehicle.writer;

import br.com.danielwisky.extractor.domains.vehicle.Metric;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetricaResource {

  private String type;

  private Double power;

  private Double torque;

  @JsonProperty("city_fuel_consumption")
  private Double cityConsumption;

  @JsonProperty("road_fuel_consumption")
  private Double roadConsumption;

  public MetricaResource(final Metric metric) {
    this.type = metric.getType();
    this.torque = metric.getTorque();
    this.power = metric.getPower();
    this.cityConsumption = metric.getCity();
    this.roadConsumption = metric.getRoad();
  }
}
