package challenge;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;

@Configuration
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    public MongoClient mongoClient() {
        return new MongoClient("localhost", 27017);
    }

    @Override
    protected String getDatabaseName() {
        return "java-mongodb";
    }

//    @Override
//    protected String getMappingBasePackage() {
//        return "challenge";
//    }
}
