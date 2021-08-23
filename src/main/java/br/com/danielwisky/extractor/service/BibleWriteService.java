package br.com.danielwisky.extractor.service;

import br.com.danielwisky.extractor.domains.Bible;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;

public class BibleWriteService {

    @SneakyThrows
    public void write(final Bible bible) {
        Files.createDirectory(Paths.get(bible.getVersion()));
    }
}