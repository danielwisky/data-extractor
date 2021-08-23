package br.com.danielwisky.extractor.commands;

import br.com.danielwisky.extractor.domains.Bible;
import br.com.danielwisky.extractor.service.BibleExtractService;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "extract")
@RequiredArgsConstructor
public class ExtractCommand implements Callable<Integer> {

    private final BibleExtractService bibleExtractService;

    @Override
    public Integer call() throws Exception {
        final Bible nvi = bibleExtractService.extract("nvi");
        return 0;
    }
}