package br.com.danielwisky.extractor.commands;

import br.com.danielwisky.extractor.domains.Bible;
import br.com.danielwisky.extractor.repositories.BibleRepository;
import br.com.danielwisky.extractor.service.BibleExtractService;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;

@Command(name = "extract")
@RequiredArgsConstructor
public class ExtractCommand implements Callable<Integer> {

  private final BibleExtractService bibleExtractService;

  private final BibleRepository bibleRepository;

  @Override
  public Integer call() {
    final Bible nvi = bibleExtractService.extract("nvi");
    bibleRepository.save(nvi);
    return 0;
  }
}
