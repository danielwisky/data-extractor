package br.com.danielwisky.mycrawler.gateways.inputs.http;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import br.com.danielwisky.mycrawler.domains.exceptions.BusinessValidationException;
import br.com.danielwisky.mycrawler.domains.exceptions.ResourceNotFoundException;
import br.com.danielwisky.mycrawler.gateways.inputs.http.resources.response.ErrorResponse;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public HttpEntity<ErrorResponse> handlerResourceNotFoundException(
      final ResourceNotFoundException ex) {
    return new ResponseEntity<>(createMessage(ex), createHeaders(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BusinessValidationException.class)
  public HttpEntity<ErrorResponse> handlerBusinessValidationException(
      final BusinessValidationException ex) {
    return new ResponseEntity<>(createMessage(ex), createHeaders(), BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public HttpEntity<ErrorResponse> handlerMethodArgumentNotValidException(
      final MethodArgumentNotValidException ex) {

    final BindingResult bindingResult = ex.getBindingResult();
    final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    final ErrorResponse message = this.processFieldErrors(fieldErrors);

    return new ResponseEntity<>(message, createHeaders(), BAD_REQUEST);
  }

  private HttpHeaders createHeaders() {
    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
    return responseHeaders;
  }

  private ErrorResponse createMessage(final Throwable ex) {
    ErrorResponse message = null;
    if (isNotBlank(ex.getMessage())) {
      message = new ErrorResponse(List.of(ex.getMessage()));
    }
    return message;
  }

  private ErrorResponse processFieldErrors(final List<FieldError> fieldErrors) {
    final List<String> errors = fieldErrors.stream()
        .map(a -> String.format("%s: %s", a.getField(), a.getDefaultMessage()))
        .collect(toList());
    return new ErrorResponse(errors);
  }
}