package br.com.danielwisky.mycrawler.gateways;

import br.com.danielwisky.mycrawler.domains.Configuration;
import java.util.List;
import java.util.Optional;

public interface ConfigurationDataGateway {

  Optional<Configuration> findById(String id);

  Configuration save(Configuration configuration);

  List<Configuration> findByType(String type);

  Optional<Configuration> findByTypeAndUrl(String type, String url);
}