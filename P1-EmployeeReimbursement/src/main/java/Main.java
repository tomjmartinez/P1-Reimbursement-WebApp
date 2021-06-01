import controller.Controller;
import dao.MongoDao;
import io.javalin.Javalin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 * main function initializes server and database
 * @author Tomas J. Martinez
 */
public class Main {
    public static final Logger logger = (Logger) LogManager.getLogger(Main.class.getName());
    public static final Logger rootLogger = (Logger) LogManager.getRootLogger();

    public static Javalin app = Javalin.create(config -> {
        config.addStaticFiles("/public");
    }).start(7777);

    public static void main(String[] args) {
        MongoDao mongoDao = new MongoDao();
        mongoDao.initDao();
        Controller controller = new Controller();

        controller.init(app);

        logger.info("initializing main app.  Inside main.");
        //app.get("/", Controller::getAllData);
        //app.get("/boomerang", Controller::getAllData);
    }
}
