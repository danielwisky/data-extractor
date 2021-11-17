package br.com.danielwisky.extractor.commands;

import br.com.danielwisky.extractor.domains.vehicle.Vehicle;
import br.com.danielwisky.extractor.repositories.GenericRepository;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class UpdateVehicleEngineService {

  private final GenericRepository genericRepository;

  public void execute() {

    List<Vehicle> vehicles = genericRepository.findVehicles();

    vehicles.stream().forEach(vehicle -> {

      String engineShort = extractEngineShort(vehicle.getEngine());

      vehicle.setEngineShort(engineShort);
      genericRepository.save(vehicle);
    });

    System.out.println("fim...");
  }

  private String extractEngineShort(String engine) {
    return Arrays.stream(StringUtils.split(engine, " ")).filter(str -> str.contains("L") && str.contains("."))
            .findFirst().orElse(null);
  }
}
