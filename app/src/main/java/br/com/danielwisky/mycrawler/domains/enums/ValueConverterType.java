package br.com.danielwisky.mycrawler.domains.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ValueConverterType {

  VALUE_FROM_HREF("valueFromHref"),
  VALUE_FROM_TEXT("valueFromText"),
  VALUE_FROM_OWN_TEXT("valueFromOwnText");

  private final String bean;
}