package tests;

import com.mongodb.client.MongoCollection;
import dao.MongoTestDao;
import modelpojos.Employee;
import modelpojos.Manager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.EmployeeService;
import services.ManagerService;

import static com.mongodb.client.model.Filters.eq;

public class ManagerServiceTest {
    ManagerService ms;

    @Before
    public void initializeDao(){
        MongoTestDao testDao = new MongoTestDao();
        testDao.initDao();
        ms = new ManagerService(testDao.database);
    }

    @Test
    public void createEmployeeTest(){

        ms.create("tj4", "123456", "what4", "nice test");

        MongoCollection<Manager> manCollection = MongoTestDao.database.getCollection("managers", Manager.class);
        Manager man = (Manager) manCollection.find().filter(eq("email", "tj4")).first();

        Assert.assertEquals("it's not writing to the database", "nice test", man.getLastname());
    }

    @Test
    public void updateTest(){
        ms.update("email", "tj4", "firstname", "for testing");
        ms.refreshCollection();
        Manager emp = ms.read("tj4");

        Assert.assertEquals("not updating properly", "for testing", emp.getFirstname());
    }

    @Test
    public void readEmployeeTest(){
        Manager man = ms.read("tj4");

        MongoCollection<Manager> manCollection = MongoTestDao.database.getCollection("managers", Manager.class);
        Manager mr = (Manager) manCollection.find().filter(eq("email", "tj4")).first();

        Assert.assertEquals("it's not reading database properly", "nice test", mr.getLastname());
    }
}
