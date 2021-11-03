package br.com.danielwisky.extractor.service;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.substring;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import br.com.danielwisky.extractor.domains.Bible;
import br.com.danielwisky.extractor.domains.Book;
import br.com.danielwisky.extractor.domains.Chapter;
import br.com.danielwisky.extractor.domains.Verse;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class BibleExtractService {

  private static final String BIBLE_URL = "https://www.bibliaonline.com.br/%s/index";

  private static final String BIBLE_QUERY = ".jss2 a";

  private static final String CHAPTER_QUERY = ".jss1 a";

  private static final String VERSE_QUERY = "p.jss43";

  private static final String HREF = "href";

  private static final String BLANK_SPACE = " ";

  public Bible extract(final String version) {
    final String bibleUrl = String.format(BIBLE_URL, version);
    final Bible bible = new Bible();
    bible.setVersion(version);
    bible.setBooks(extractedBooks(bibleUrl));
    return bible;
  }

  @SneakyThrows
  private List<Book> extractedBooks(final String bibleUrl) {
    System.out.println(String.format("reading %s ...", bibleUrl));
    final Document bibleAsDoc = Jsoup.connect(bibleUrl).get();
    return bibleAsDoc.select(BIBLE_QUERY)
            .stream()
            .map(this::buildBook)
            .collect(toList());
  }

  @SneakyThrows
  private List<Chapter> extractChapters(final String hrefChapter) {
    System.out.println(String.format("reading %s ...", hrefChapter));
    final Document chapterAsDoc = Jsoup.connect(hrefChapter).get();
    return chapterAsDoc.select(CHAPTER_QUERY)
            .stream()
            .map(this::buildChapter)
            .collect(toList());
  }

  @SneakyThrows
  private List<Verse> extractVerses(final String hrefVerse) {
    System.out.println(String.format("reading %s ...", hrefVerse));
    final Document chapterAsDoc = Jsoup.connect(hrefVerse).get();
    return chapterAsDoc.select(VERSE_QUERY)
            .stream()
            .map(this::buildVerse)
            .collect(toList());
  }

  private Verse buildVerse(final Element verseAsElement) {
    final Verse verse = new Verse();
    final String verseAstext = verseAsElement.text();
    verse.setNumber(Integer.valueOf(substring(verseAstext, INTEGER_ZERO, verseAstext.indexOf(BLANK_SPACE))));
    verse.setVerse(trim(StringUtils.substring(verseAstext, verseAstext.indexOf(BLANK_SPACE))));
    return verse;
  }

  private Book buildBook(final Element bookAsElement) {
    final Book book = new Book();
    book.setTitle(trim(bookAsElement.text()));
    book.setChapters(extractChapters(bookAsElement.attr(HREF)));
    return book;
  }

  private Chapter buildChapter(final Element chapterAsElement) {
    final Chapter chapter = new Chapter();
    chapter.setChapter(Integer.valueOf(chapterAsElement.text()));
    chapter.setVerses(extractVerses(chapterAsElement.attr(HREF)));
    return chapter;
  }
}