package br.com.danielwisky.extractor.commands;

import br.com.danielwisky.extractor.domains.bible.Bible;
import br.com.danielwisky.extractor.repositories.GenericRepository;
import br.com.danielwisky.extractor.service.BibleExtractService;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;

@Command(name = "extract-bible")
@RequiredArgsConstructor
public class ExtractBibleCommand implements Callable<Integer> {

  private final BibleExtractService bibleExtractService;

  private final GenericRepository genericRepository;

  @Override
  public Integer call() {
    final Bible nvi = bibleExtractService.extract("nvi");
    genericRepository.save(nvi);
    return 0;
  }
}
