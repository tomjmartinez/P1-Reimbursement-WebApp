package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dao.MongoDao;
import io.javalin.http.Context;
import modelpojos.Employee;
import modelpojos.Manager;
import modelpojos.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

/** Service layer tasks to aid performing Manager tasks
 * @author Tomas J. Martinez
 */
public class ManagerService implements UserService{
    MongoDatabase db;
    MongoCollection<Manager> manCollection; // = MongoTestDao.database.getCollection("managers", Manager.class);
    private static final Logger logger = LogManager.getLogger(ManagerService.class);


    public ManagerService() {
        db = MongoDao.database;
    }

    public ManagerService(MongoDatabase db) {
        this.db = db;
    }

    /**
     * crud db method creates new manager
     * @param newUser
     * @param pass
     * @param fname
     * @param lname
     */
    @Override
    public void create(String newUser, String pass, String fname, String lname) {
        refreshCollection();
        Manager man1 = new Manager(newUser, pass, fname, lname);

        manCollection.insertOne(man1);
        logger.info("created new manager in db");
    }

    /**
     * crud db method reads manager
     * @param email
     * @return manager found
     */
    @Override
    public Manager read(String email) {
        refreshCollection();
        Manager man = (Manager) manCollection.find().filter(eq("email", email)).first();
        logger.info("reading new manager in db" + man);
        return man;
    }

    /**
     * crud db method updates manager for chosen field
     * @param field
     * @param match
     * @param column
     * @param value
     */
    @Override
    public void update(String field, String match, String column, String value) {
        refreshCollection();
        manCollection.updateOne(
                eq(field, match),
                combine(set(column, value)));
        refreshCollection();
        logger.info("updating new manager in db" + match);
    }

    /**
     * reloads collection to freshest state
     */
    public void refreshCollection(){
        if(db == null){
            db = MongoDao.database;
        }
        manCollection = db.getCollection("managers", Manager.class);
    }
}
