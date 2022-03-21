package br.com.danielwisky.mycrawler.gateways.outputs.mongodb.repositories;

import br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents.CrawlerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CrawlerRepository extends MongoRepository<CrawlerDocument, String> {

}