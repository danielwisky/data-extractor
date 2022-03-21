package br.com.danielwisky.mycrawler.domains.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(final String message) {
    super(message);
  }
}