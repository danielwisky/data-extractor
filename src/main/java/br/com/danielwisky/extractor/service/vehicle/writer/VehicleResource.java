package br.com.danielwisky.extractor.service.vehicle.writer;

import br.com.danielwisky.extractor.domains.vehicle.Vehicle;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleResource {

  private Long id;

  private String name;

  private String year;

  private String engine;

  @JsonProperty("engine_desc")
  private String engineDescription;

  @JsonProperty("top_speed")
  private Double topspeed;

  @JsonProperty("time")
  private Double tempo0a100;

  private Double weight;

  private List<MetricaResource> metrics;

  public VehicleResource(final Vehicle vehicle, final String name) {
    this.id = vehicle.getId();
    this.name = name;
    this.year = vehicle.getYear();
    this.engine = vehicle.getEngineShort();
    this.engineDescription = vehicle.getEngine();
    this.weight = vehicle.getWeight();
    this.topspeed = vehicle.getSpeed();
    this.tempo0a100 = vehicle.getTime0to100();
    this.metrics = vehicle.getMetrics().stream().map(MetricaResource::new).collect(Collectors.toList());
  }
}
