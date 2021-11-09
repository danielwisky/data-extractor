package br.com.danielwisky.extractor.commands;

import br.com.danielwisky.extractor.service.VehicleExtractService;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;

@Command(name = "extract-vehicle")
@RequiredArgsConstructor
public class ExtractVehicleCommand implements Callable<Integer> {

  private final VehicleExtractService vehicleExtractService;

  @Override
  public Integer call() {
    vehicleExtractService.extract();
    return 0;
  }
}
