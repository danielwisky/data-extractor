package br.com.danielwisky.extractor.domains;

import lombok.Data;

import java.util.List;

@Data
public class Book {

    private String title;
    private List<Chapter> chapters;
}