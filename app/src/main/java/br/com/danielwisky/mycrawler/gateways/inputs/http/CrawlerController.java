package br.com.danielwisky.mycrawler.gateways.inputs.http;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.OK;

import br.com.danielwisky.mycrawler.gateways.inputs.http.resources.request.CrawlerRequest;
import br.com.danielwisky.mycrawler.gateways.inputs.http.resources.response.CrawlerResponse;
import br.com.danielwisky.mycrawler.usecases.CreateCrawler;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/crawlers")
public class CrawlerController {

  private final CreateCrawler createCrawler;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(OK)
  @Operation(description = "Create a crawler")
  public ResponseEntity<List<CrawlerResponse>> create(
      @RequestBody @Valid final CrawlerRequest crawlerRequest) {
    return ResponseEntity.ok(
        createCrawler.execute(crawlerRequest.getType(), crawlerRequest.getUrl())
            .stream()
            .map(CrawlerResponse::new)
            .collect(toList()));
  }
}