package br.com.danielwisky.mycrawler.usecases;

import br.com.danielwisky.mycrawler.domains.Configuration;
import br.com.danielwisky.mycrawler.gateways.ConfigurationDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateConfiguration {

  private final ConfigurationDataGateway configurationDataGateway;

  public Configuration execute(final Configuration configuration) {
    return configurationDataGateway.save(configuration);
  }
}