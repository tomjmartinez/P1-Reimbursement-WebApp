package tests;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import controller.Controller;
import dao.MongoDao;
import dao.MongoTestDao;
import io.javalin.http.Context;
import modelpojos.MoneyRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.RequestsService;
import org.mockito.*;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;
//import static org.mockito.Mockito.*;

public class RequestServiceTest {
    RequestsService rs; // = new RequestsService();
    //Context ctx = Mockito.mock(Context.class);

    @Before
    public void initializeDao(){
        MongoTestDao testDao = new MongoTestDao();
        testDao.initDao();
        rs = new RequestsService(testDao.database);
    }

    @Test
    public void randomInRangeTest(){
        String output = rs.randAccountNum();
        int digits = output.length();
        boolean inRange = digits < 9;
        Assert.assertEquals("Not in range", true, inRange);
    }

    @Test
    public void createRequestTest(){

        rs.create("tj4", 5d, "what4", "yes");

        MongoCollection<MoneyRequest> mrCollection = MongoTestDao.database.getCollection("moneyrequests", MoneyRequest.class);
        MoneyRequest mr = (MoneyRequest) mrCollection.find().filter(eq("employee", "tj4")).first();

        Assert.assertEquals("it's not writing to the database", "what4", mr.getReason());
    }

    @Test
    public void readRequestTest(){
        MoneyRequest testMR = rs.read("tj4");

        MongoCollection<MoneyRequest> mrCollection = MongoTestDao.database.getCollection("moneyrequests", MoneyRequest.class);
        MoneyRequest mr = (MoneyRequest) mrCollection.find().filter(eq("employee", "tj4")).first();

        Assert.assertEquals("it's not reading database properly", "what4", mr.getReason());
    }

    @Test
    public void readAllTest(){
        List<MoneyRequest> list = rs.readAll("tj4");
        for(MoneyRequest li : list) {
            Assert.assertEquals("it's not reading all properly", "tj4", li.getEmployee());
        }
    }

    @Test
    public void updateTest(){
        rs.update("employee", "tj4", "comments", "for testing");
        rs.refreshCollection();
        MoneyRequest mr = rs.read("tj4");

        Assert.assertEquals("not updating properly", "for testing", mr.getComments());
    }
}
