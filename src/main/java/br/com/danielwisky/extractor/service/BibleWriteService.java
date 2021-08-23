package br.com.danielwisky.extractor.service;

import br.com.danielwisky.extractor.domains.Bible;
import br.com.danielwisky.extractor.domains.Book;
import br.com.danielwisky.extractor.domains.Chapter;
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

    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public void write(final Bible bible) {
        createDirectory(Paths.get(bible.getVersion()));

        for (Book book : bible.getBooks()) {
            final String titleNormalized = normalize(book.getTitle());
            createDirectory(Paths.get(String.format("%s/%s", bible.getVersion(), titleNormalized)));

            for (Chapter chapter : book.getChapters()) {
                FileWriter file = new FileWriter(String.format("%s/%s/%s.json", bible.getVersion(), titleNormalized, chapter.getChapter()));
                file.write(objectMapper.writeValueAsString(chapter.getVerses()));
                file.flush();
            }
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