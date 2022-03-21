package br.com.danielwisky.mycrawler.domains.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FilterConterterType {

  REPLACE_TO_EMPTY("filterReplaceToEmpty"),
  UPPERCASE("filterUppercase"),
  SUBSTRING_AFTER("filterSubstringAfter"),
  SUBSTRING_AFTER_LAST("filterSubstringAfterLast"),
  SUBSTRING_BEFORE("filterSubstringBefore");

  private final String bean;

}