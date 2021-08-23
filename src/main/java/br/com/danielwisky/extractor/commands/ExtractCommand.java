package br.com.danielwisky.extractor.commands;

import br.com.danielwisky.extractor.domains.Bible;
import br.com.danielwisky.extractor.service.BibleExtractService;
import br.com.danielwisky.extractor.service.BibleWriteService;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "extract")
@RequiredArgsConstructor
public class ExtractCommand implements Callable<Integer> {

    private final BibleExtractService bibleExtractService;
    private final BibleWriteService bibleWriteService;

    @Override
    public Integer call() throws Exception {
        final Bible nvi = bibleExtractService.extract("nvi");
        bibleWriteService.write(nvi);
        return 0;
    }
}