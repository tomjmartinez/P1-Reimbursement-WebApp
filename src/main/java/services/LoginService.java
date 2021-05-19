package services;
import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mongodb.client.MongoCollection;
import dao.MongoDao;
import modelpojos.Employee;
import modelpojos.Manager;
import modelpojos.User;
import org.bson.types.ObjectId;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/** Service layer tasks to aid performing Client tasks
 * @author Tomas J. Martinez
 */
public class LoginService {
    private static final Logger logger = LogManager.getLogger(LoginService.class);

    /** function logs in a user
     * @param username
     * @param password
     * @param usertype
     * @return Client object representing successful user that was logged in
     */
    public User login(String username, String password, String usertype) {

        System.out.println(username);
        System.out.println(password);
        System.out.println(usertype);

        UserService userServ;
        if (usertype.equals("employee")){
            userServ = new EmployeeService();
        } else if(usertype.equals( "manager")){
            userServ = new ManagerService();
        } else {
            logger.debug("wrong usertype coming in!!");
            System.out.println("what is this?" + usertype.getClass());
            return null;
        }

        System.out.println("userv" + userServ);
        User user = userServ.read(username);
        System.out.println(user.toString());



        if((user != null) && user.getEmail().equals(username) && user.getPassword().equals(password)){

            System.out.println("Successful Login. Welcome!");
            logger.info("Successful Login for: " + user.getEmail());

            //Add session cookie here
            return user;
        } else {
            System.out.println("Invalid username or password.");
            logger.warn("Invalid Login for: " + user.getEmail());

            return null;
        }
    }

    /*
    public void updateCurrentUser(ObjectId id) {
    }*/
}
