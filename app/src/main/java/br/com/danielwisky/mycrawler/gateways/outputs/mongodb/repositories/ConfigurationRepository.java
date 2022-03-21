package br.com.danielwisky.mycrawler.gateways.outputs.mongodb.repositories;

import br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents.ConfigurationDocument;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigurationRepository extends MongoRepository<ConfigurationDocument, String> {

  List<ConfigurationDocument> findByType(String domain);

  Optional<ConfigurationDocument> findByTypeAndUrl(String type, String url);
}