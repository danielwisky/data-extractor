package br.com.danielwisky.mycrawler.usecases;

import br.com.danielwisky.mycrawler.domains.Configuration;
import br.com.danielwisky.mycrawler.gateways.ConfigurationDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateConfiguration {

  private final ConfigurationDataGateway configurationDataGateway;

  public Configuration execute(final String id, final Configuration configuration) {
    configuration.setId(id);
    return configurationDataGateway.save(configuration);
  }
}