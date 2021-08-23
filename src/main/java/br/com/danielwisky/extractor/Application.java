package br.com.danielwisky.extractor;

import br.com.danielwisky.extractor.commands.ExtractCommand;
import br.com.danielwisky.extractor.service.BibleExtractService;
import picocli.CommandLine;

public class Application {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ExtractCommand(new BibleExtractService())).execute(args);
        System.exit(exitCode);
    }
}