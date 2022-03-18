package br.com.danielwisky.mycrawler.gateways;

import br.com.danielwisky.mycrawler.domains.Configuration;

public interface ConfigurationDataGateway {

  Configuration save(Configuration configuration);

}