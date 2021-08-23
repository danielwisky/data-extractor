package br.com.danielwisky.extractor.domains;

import lombok.Data;

import java.util.List;

@Data
public class Chapter {

    private Integer chapter;
    private List<Verse> verses;
}