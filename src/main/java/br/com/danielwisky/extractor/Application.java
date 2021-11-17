package br.com.danielwisky.extractor;

import br.com.danielwisky.extractor.commands.UpdateVehicleEngineService;
import br.com.danielwisky.extractor.commands.WriteVehicleCommand;
import br.com.danielwisky.extractor.repositories.GenericRepository;
import br.com.danielwisky.extractor.service.vehicle.writer.WriteVehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;

public class Application {

  public static void main(String[] args) {
    //final ExtractBibleCommand extractBibleCommand = new ExtractBibleCommand(new BibleExtractService(), new BibleRepository());
    //final ExtractVehicleCommand extractVehicleCommand = new ExtractVehicleCommand(new ShopcarExtractService(new GenericRepository()));
    final GenericRepository genericRepository = new GenericRepository();
    final ObjectMapper objectMapper = new ObjectMapper();
    final WriteVehicleCommand writeVehicleCommand = new WriteVehicleCommand(
            new WriteVehicleService(genericRepository, objectMapper),
            new UpdateVehicleEngineService(genericRepository));
    final int exitCode = new CommandLine(writeVehicleCommand).execute(args);
    System.exit(exitCode);
  }
}