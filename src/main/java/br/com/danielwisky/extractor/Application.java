package br.com.danielwisky.extractor;

import br.com.danielwisky.extractor.commands.WriteVehicleCommand;
import br.com.danielwisky.extractor.repositories.GenericRepository;
import br.com.danielwisky.extractor.service.vehicle.writer.WriteVehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;

public class Application {

  public static void main(String[] args) {
    //final ExtractBibleCommand extractBibleCommand = new ExtractBibleCommand(new BibleExtractService(), new BibleRepository());
    //final ExtractVehicleCommand extractVehicleCommand = new ExtractVehicleCommand(new ShopcarExtractService(new GenericRepository()));
    final WriteVehicleCommand writeVehicleCommand = new WriteVehicleCommand(new WriteVehicleService(new GenericRepository(), new ObjectMapper()));
    final int exitCode = new CommandLine(writeVehicleCommand).execute(args);
    System.exit(exitCode);
  }
}