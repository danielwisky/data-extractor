package br.com.danielwisky.mycrawler.domains.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ValueConverterType {

  VALUE_FROM_HREF("valueFromHref"),
  VALUE_FROM_TEXT("valueFromText");

  private final String bean;

}