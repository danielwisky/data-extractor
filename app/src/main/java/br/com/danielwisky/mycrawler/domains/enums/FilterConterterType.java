package br.com.danielwisky.mycrawler.domains.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FilterConterterType {

  REPLACE_TO_EMPTY("filterReplaceToEmpty"),
  UPPERCASE("filterUppercase");

  private final String bean;

}