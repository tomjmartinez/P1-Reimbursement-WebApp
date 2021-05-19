package dao;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * class holds and creates Mongo database connection
 * @author Tomas J. Martinez
 */
public class MongoTestDao {
    public static MongoDatabase database;
    /** Initializes database connection
     */
    public void initDao(){
        try {
            ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/boomerangtest");
            CodecProvider codecProvider = PojoCodecProvider.builder().register("modelpojos").build();
            CodecRegistry registry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(codecProvider));
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .retryWrites(true)
                    .codecRegistry(registry)
                    .build();
            MongoClient client = MongoClients.create(settings);
            database = client.getDatabase("boomerangtest");


        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
