package br.com.danielwisky.mycrawler.gateways.inputs.http;

import static org.springframework.http.HttpStatus.ACCEPTED;

import br.com.danielwisky.mycrawler.gateways.inputs.http.resources.request.CrawlerRequest;
import br.com.danielwisky.mycrawler.usecases.CreateCrawler;
import io.swagger.annotations.ApiOperation;
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

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(ACCEPTED)
  @ApiOperation(value = "Create a crawler")
  public ResponseEntity create(
      @RequestBody @Valid final CrawlerRequest crawlerRequest) {
    createCrawler.execute(crawlerRequest.toDomain());
    return ResponseEntity.accepted().build();
  }
}