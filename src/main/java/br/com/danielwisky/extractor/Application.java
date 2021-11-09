package br.com.danielwisky.extractor;

import br.com.danielwisky.extractor.commands.ExtractVehicleCommand;
import br.com.danielwisky.extractor.repositories.GenericRepository;
import br.com.danielwisky.extractor.service.vehicle.ShopcarExtractService;
import picocli.CommandLine;

public class Application {

  public static void main(String[] args) {
    //final ExtractBibleCommand extractBibleCommand = new ExtractBibleCommand(new BibleExtractService(), new BibleRepository());
    final ExtractVehicleCommand extractVehicleCommand = new ExtractVehicleCommand(new ShopcarExtractService(new GenericRepository()));
    final int exitCode = new CommandLine(extractVehicleCommand).execute(args);
    System.exit(exitCode);
  }
}