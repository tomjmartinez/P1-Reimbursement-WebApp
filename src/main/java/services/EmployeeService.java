package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dao.MongoDao;
import io.javalin.http.Context;
import modelpojos.Employee;
import modelpojos.MoneyRequest;
import modelpojos.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

/** Service layer tasks to aid performing Employee tasks
 * @author Tomas J. Martinez
 */
public class EmployeeService implements UserService{
    MongoDatabase db;
    MongoCollection<Employee> eCollection; // = db.getCollection("employees", Employee.class);
    private static final Logger logger = LogManager.getLogger(EmployeeService.class);

    public EmployeeService() {
        this.db = MongoDao.database;
    }

    public EmployeeService(MongoDatabase db) {
        this.db = db;
    }


    /**
     * db crud operation create
     * @param newUser
     * @param pass
     * @param newFirstName
     * @param newLastName
     */
    @Override
    public void create(String newUser,String pass, String newFirstName, String newLastName)
     {
        refreshCollection();
        //String currentUser = ctx.sessionAttribute("currentUser");


        Employee emp = new Employee(newUser, pass, newFirstName, newLastName, null);

        eCollection.insertOne(emp);
        logger.info("created new employee in db");
    }

    /**
     * db crud operation read
     * @param email
     * @return
     */
    @Override
    public Employee read(String email) {
        refreshCollection();
        Employee emp = (Employee) eCollection.find().filter(eq("email", email)).first();
        logger.info("read new employee in db");
        return emp;
    }

    /**
     * crud operation update
     * @param field
     * @param match
     * @param column
     * @param value
     */
    @Override
    public void update(String field, String match, String column, String value) {
        refreshCollection();
        eCollection.updateOne(
                eq(field, match),
                combine(set(column, value)));
        refreshCollection();
        logger.info("updated new employee in db");
    }

    /**
     * function to get latest version of database
     */
    public void refreshCollection(){
        logger.info("refreshing employees db");
        eCollection = db.getCollection("employees", Employee.class);
    }
}
