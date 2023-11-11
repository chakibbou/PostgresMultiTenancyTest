package com.smt.springmultitenancy.infrastructure.database.mongo;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Mono;

@EnableReactiveMongoRepositories
@Configuration
@ConditionalOnProperty(value = "smt.configuration.database.mongo.enable", havingValue = "true")
public class MultiTenantMongoDBFactory extends SimpleReactiveMongoDatabaseFactory {
    private final MongoReactiveConfiguration mongoReactiveConfiguration;

    public MultiTenantMongoDBFactory(@Qualifier("createMongoClient") MongoClient mongoClient,
                                     String databaseName,
                                     MongoReactiveConfiguration mongoReactiveConfiguration) {
        super(mongoClient, databaseName);
        this.mongoReactiveConfiguration = mongoReactiveConfiguration;
    }

    public Mono<MongoDatabase> getMongoDatabase() throws DataAccessException {
        return mongoReactiveConfiguration.mongoDatabaseCurrentTenantResolver();
    }
}
