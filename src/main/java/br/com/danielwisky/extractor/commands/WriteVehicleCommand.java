package br.com.danielwisky.extractor.commands;

import br.com.danielwisky.extractor.service.vehicle.writer.WriteVehicleService;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;

@Command(name = "write-vehicle")
@RequiredArgsConstructor
public class WriteVehicleCommand implements Callable<Integer> {

  private final WriteVehicleService writeVehicleService;

  @Override
  public Integer call() {
    writeVehicleService.write();
    return 0;
  }
}
