package br.com.danielwisky.mycrawler.gateways.outputs.mongodb.repositories;

import br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents.CrawlerLineDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CrawlerLineRepository extends MongoRepository<CrawlerLineDocument, String> {

}