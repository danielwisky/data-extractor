package br.com.danielwisky.mycrawler.usecases;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;

import br.com.danielwisky.mycrawler.domains.Configuration;
import br.com.danielwisky.mycrawler.domains.exceptions.ResourceNotFoundException;
import br.com.danielwisky.mycrawler.gateways.ConfigurationDataGateway;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindConfigurationsByObjectTypeAndUrl {

  private final ConfigurationDataGateway configurationDataGateway;

  public List<Configuration> execute(final String objectType, final String url) {
    final List<Configuration> configurations = findBy(objectType, url);
    if (isEmpty(configurations)) {
      throw new ResourceNotFoundException("Configuration not found.");
    }

    return configurations;
  }

  private List<Configuration> findBy(final String objectType, final String url) {
    return StringUtils.isBlank(url)
        ? configurationDataGateway.findByObjectType(objectType)
        : configurationDataGateway.findByObjectTypeAndUrl(objectType, url)
            .map(Arrays::asList).orElse(null);
  }
}