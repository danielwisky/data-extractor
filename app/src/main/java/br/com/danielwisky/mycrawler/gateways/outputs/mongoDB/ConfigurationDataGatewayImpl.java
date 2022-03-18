package br.com.danielwisky.mycrawler.gateways.outputs.mongoDB;

import br.com.danielwisky.mycrawler.domains.Configuration;
import br.com.danielwisky.mycrawler.gateways.ConfigurationDataGateway;
import br.com.danielwisky.mycrawler.gateways.outputs.mongoDB.documents.ConfigurationDocument;
import br.com.danielwisky.mycrawler.gateways.outputs.mongoDB.repositories.ConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfigurationDataGatewayImpl implements ConfigurationDataGateway {

  private final ConfigurationRepository configurationRepository;

  @Override
  public Configuration save(final Configuration configuration) {
    return configurationRepository.save(new ConfigurationDocument(configuration)).toDomain();
  }
}