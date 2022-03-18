package br.com.danielwisky.mycrawler.gateways.outputs.mongoDB.repositories;

import br.com.danielwisky.mycrawler.gateways.outputs.mongoDB.documents.CrawlerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CrawlerRepository extends MongoRepository<CrawlerDocument, String> {

}