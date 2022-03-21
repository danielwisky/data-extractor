package br.com.danielwisky.mycrawler.gateways.outputs.mongodb;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

import br.com.danielwisky.mycrawler.domains.Configuration;
import br.com.danielwisky.mycrawler.gateways.ConfigurationDataGateway;
import br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents.ConfigurationDocument;
import br.com.danielwisky.mycrawler.gateways.outputs.mongodb.repositories.ConfigurationRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfigurationDataGatewayImpl implements ConfigurationDataGateway {

  private final ConfigurationRepository configurationRepository;

  @Override
  public Optional<Configuration> findById(final String id) {
    return configurationRepository.findById(id).map(ConfigurationDocument::toDomain);
  }

  @Override
  public Configuration save(final Configuration configuration) {
    return configurationRepository.save(new ConfigurationDocument(configuration)).toDomain();
  }

  @Override
  public List<Configuration> findByType(final String type) {
    return emptyIfNull(configurationRepository.findByType(type))
        .stream()
        .map(ConfigurationDocument::toDomain)
        .collect(toList());
  }

  @Override
  public Optional<Configuration> findByTypeAndUrl(final String type, final String url) {
    return configurationRepository.findByTypeAndUrl(type, url)
        .map(ConfigurationDocument::toDomain);
  }
}