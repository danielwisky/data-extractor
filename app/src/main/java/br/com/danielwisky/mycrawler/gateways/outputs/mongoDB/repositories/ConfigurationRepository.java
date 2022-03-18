package br.com.danielwisky.mycrawler.gateways.outputs.mongoDB.repositories;

import br.com.danielwisky.mycrawler.gateways.outputs.mongoDB.documents.ConfigurationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigurationRepository extends MongoRepository<ConfigurationDocument, String> {

}