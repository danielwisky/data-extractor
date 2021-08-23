package br.com.danielwisky.extractor.domains;

import lombok.Data;

import java.util.List;

@Data
public class Bible {

    private String version;
    private List<Book> books;
}