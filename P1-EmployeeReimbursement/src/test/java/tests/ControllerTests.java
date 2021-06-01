package tests;

import controller.Controller;
import dao.MongoDao;
import dao.MongoTestDao;
import io.javalin.Javalin;
import org.junit.Assert;
import org.junit.Before;

public class ControllerTests {

    public static Javalin app = Javalin.create(config -> {

    }).start(7771);

    @Before
    public void initTest() {
        MongoTestDao mongo = new MongoTestDao();
        mongo.initDao();
        Controller testController = new Controller();

        testController.init(app);

        Assert.assertNotEquals("error", null, testController);
    }

}
