package tests;

import com.mongodb.client.MongoCollection;
import dao.MongoTestDao;
import io.javalin.http.Context;
import modelpojos.Employee;
import modelpojos.MoneyRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.EmployeeService;
import services.RequestsService;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class EmployeeServiceTest {
    EmployeeService es;

    @Before
    public void initializeDao(){
        MongoTestDao testDao = new MongoTestDao();
        testDao.initDao();
        es = new EmployeeService(testDao.database);
    }



    @Test
    public void createEmployeeTest(){

        es.create("tj4", "123456", "what4", "nice test");

        MongoCollection<Employee> eCollection = MongoTestDao.database.getCollection("employees", Employee.class);
        Employee emp = (Employee) eCollection.find().filter(eq("email", "tj4")).first();

        Assert.assertEquals("it's not writing to the database", "nice test", emp.getLastname());
    }


    @Test
    public void readEmployeeTest(){
        Employee testMR = es.read("tj4");

        MongoCollection<Employee> eCollection = MongoTestDao.database.getCollection("employees", Employee.class);
        Employee mr = (Employee) eCollection.find().filter(eq("email", "tj4")).first();

        Assert.assertEquals("it's not reading database properly", "nice test", mr.getLastname());
    }

    /*
    @Test
    public void readAllTest(){
        List<Employee> list = es.readAll("tj4");
        for(Employee li : list) {
            Assert.assertEquals("it's not reading all properly", "tj4", li.getEmail());
        }
    }

     */

    @Test
    public void updateTest(){
        es.update("email", "tj4", "firstname", "for testing");
        es.refreshCollection();
        Employee emp = es.read("tj4");

        Assert.assertEquals("not updating properly", "for testing", emp.getFirstname());
    }
}
