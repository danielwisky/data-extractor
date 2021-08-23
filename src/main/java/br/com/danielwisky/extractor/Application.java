package br.com.danielwisky.extractor;

import br.com.danielwisky.extractor.commands.ExtractCommand;
import br.com.danielwisky.extractor.service.BibleExtractService;
import br.com.danielwisky.extractor.service.BibleWriteService;
import picocli.CommandLine;

public class Application {

    public static void main(String[] args) {
        final ExtractCommand extractCommand = new ExtractCommand(new BibleExtractService(), new BibleWriteService());
        final int exitCode = new CommandLine(extractCommand).execute(args);
        System.exit(exitCode);
    }
}