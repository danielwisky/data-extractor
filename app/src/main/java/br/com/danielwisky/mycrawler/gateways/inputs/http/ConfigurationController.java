package br.com.danielwisky.mycrawler.gateways.inputs.http;

import static org.springframework.http.HttpStatus.OK;

import br.com.danielwisky.mycrawler.gateways.inputs.http.resources.request.ConfigurationRequest;
import br.com.danielwisky.mycrawler.gateways.inputs.http.resources.response.ConfigurationResponse;
import br.com.danielwisky.mycrawler.usecases.CreateConfiguration;
import br.com.danielwisky.mycrawler.usecases.UpdateConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/configurations")
public class ConfigurationController {

  private final CreateConfiguration createConfiguration;
  private final UpdateConfiguration updateConfiguration;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(OK)
  @Operation(description = "Create configuration")
  public ResponseEntity<ConfigurationResponse> create(
      @RequestBody @Valid final ConfigurationRequest configurationRequest) {
    return ResponseEntity.ok(new ConfigurationResponse(
        createConfiguration.execute(configurationRequest.toDomain())));
  }

  @PutMapping(
      value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(OK)
  @Operation(description = "Update configuration")
  public ResponseEntity<ConfigurationResponse> update(
      @PathVariable final String id,
      @RequestBody @Valid final ConfigurationRequest configurationRequest) {
    return ResponseEntity.ok(new ConfigurationResponse(
        updateConfiguration.execute(id, configurationRequest.toDomain())));
  }
}
