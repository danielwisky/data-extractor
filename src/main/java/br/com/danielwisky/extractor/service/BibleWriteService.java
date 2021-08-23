package br.com.danielwisky.extractor.service;

import br.com.danielwisky.extractor.domains.Bible;
import br.com.danielwisky.extractor.domains.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.lowerCase;

public class BibleWriteService {

    private static final String BLANK_SPACE = " ";
    private static final String MINUS = "-";
    private static final String BIBLE = "bible";

    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public void write(final Bible bible) {
        createDirectory(Paths.get(BIBLE));
        createDirectory(Paths.get(String.format("%s/%s", BIBLE, bible.getVersion())));

        for (Book book : bible.getBooks()) {
            final String titleNormalized = normalize(book.getTitle());
            final FileWriter file = new FileWriter(String.format("%s/%s/%s.json", BIBLE, bible.getVersion(), titleNormalized));
            file.write(objectMapper.writeValueAsString(book.getChapters()));
            file.flush();
            file.close();
        }
    }

    private void createDirectory(final Path path) {
        try {
            Files.createDirectory(path);
        } catch (Exception e) {
            // nothing
        }
    }

    private static String normalize(final String str) {
        final String normalize = Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", EMPTY).replaceAll(BLANK_SPACE, MINUS);
        return lowerCase(normalize);
    }
}