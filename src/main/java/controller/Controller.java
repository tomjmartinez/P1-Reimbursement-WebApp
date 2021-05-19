package controller;

import com.mongodb.client.MongoCollection;
import io.javalin.Javalin;
import io.javalin.http.Context;
import modelpojos.Employee;
import modelpojos.Manager;
import modelpojos.MoneyRequest;
import modelpojos.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import services.*;

import java.util.List;

/** Controller contains all routes for web application
 * @author Tomas J. Martinez
 * @version 0.5
 * @since 0.0
 */
public class Controller {
    Javalin app;
    LoginService loginServ = new LoginService();

    /**logger
     */
    public static final Logger logger = (Logger) LogManager.getLogger(Controller.class.getName());

    public Controller() {
    }

    /**
     * all the routes for the http requests
     * @param application represents a Javalin server/application
     */
    public void init(Javalin application) {
        this.app = application;
        app.post("/login", ctx -> {
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");
            String usertype = ctx.formParam("usertype");
            User currentUser = loginServ.login(username, password, usertype);
            System.out.println(currentUser);

            if(currentUser != null){
                ctx.sessionAttribute("currentUser", currentUser.getEmail());
            }

            if(currentUser == null){
                ctx.redirect("/login.html");
            }else if( currentUser instanceof Employee) {
                ctx.sessionAttribute("currentUser",currentUser.getEmail());
                ctx.sessionAttribute("currentUserType","employee");
                ctx.json(currentUser);
                ctx.redirect("employee.html");
            } else if(currentUser instanceof Manager){
                ctx.sessionAttribute("currentUser",currentUser.getEmail());
                ctx.sessionAttribute("currentUserType","manager");
                ctx.redirect("manager.html");
            } else{
                ctx.redirect("/login");
            }
        });

        app.get("/logout", ctx -> {
            ctx.redirect("/index.html");

            //remove session ctx.session
            ctx.sessionAttribute("currentUser", null);
            logger.debug("After logout: currentUser is now:" + ctx.sessionAttribute("currentUser"));
        });

        app.post("/updateprofile", ctx -> {
            //FIXME Add a session current user checker..

            UserService userServ = null;
            if(ctx.sessionAttribute("currentUserType") == "employee") {
                userServ = new EmployeeService();
            } else if(ctx.sessionAttribute("currentUserType") == "manager"){
                userServ = new ManagerService();
            }

            User currentUser = userServ.read(ctx.sessionAttribute("currentUser"));
            System.out.println(ctx.formParam("email"));
            System.out.println("trying to update");
            System.out.println("current user inside updating is: " + currentUser);

            //update email
            userServ.update("email", currentUser.getEmail(), "email", ctx.formParam("email"));
            logger.info("updated email for user" + currentUser);

            //update password
            userServ.update("email", currentUser.getEmail(), "password", ctx.formParam("password"));
            logger.info("trying to update password for user" + currentUser);

            currentUser = userServ.read(currentUser.getEmail());
            //System.out.println("updating to new user email: " + currentUser.getEmail());
            try {
                ctx.sessionAttribute("currentUser", currentUser.getEmail());
            } catch(Exception e){
                e.printStackTrace();
            }
        });

        app.get("/api/employee", ctx -> {
            //ctx.html("You are in the post inside /employee");
            String uEmail = ctx.sessionAttribute("currentUser");
            //UserService uServ = null;
            RequestsService rService = new RequestsService();
            List<MoneyRequest> mc = rService.readAll(uEmail);
            System.out.println("this is supposed to be records" + mc);
            ctx.json(mc);
        });

        /*
        app.post("/api/employee", ctx -> {

        });
        *
         */

        app.post("/api/new-request", ctx -> {
            //ctx.html("You are in the post inside /employee");
            String uEmail = ctx.sessionAttribute("currentUser");
            //UserService uServ = null;
            RequestsService rService = new RequestsService();
            String currentUser = ctx.sessionAttribute("currentUser");
            Double amount = Double.parseDouble(ctx.formParam("amount"));
            String reason = ctx.formParam("reason");
            String comments = ctx.formParam("comments");
            rService.create(currentUser, amount, reason, comments);
            System.out.println("successful new money request made");
            logger.debug("new request for: " + uEmail + " about: " + ctx.formParam("reason"));
        });

        app.get("/api/currentUser", ctx -> {
            String currentUser = ctx.sessionAttribute("currentUser");
            System.out.println("inside the controller route current user: " + currentUser + ". ctx body is:" + ctx.body());
            ctx.json(currentUser);
        });

        app.get("/api/pending", ctx -> {
            //ctx.html("You are in the post inside /employee");
            String uEmail = ctx.sessionAttribute("currentUser");
            //UserService uServ = null;
            RequestsService rService = new RequestsService();
            List<MoneyRequest> mc = rService.readAll("pending");
            System.out.println("this is supposed to be records" + mc);
            ctx.json(mc);
        });

        app.post("/api/approve-request", ctx -> {
            String requestNum = ctx.body();
            String currentManager = ctx.sessionAttribute("currentUser");
            RequestsService rService = new RequestsService();
            rService.update("requestNumber", requestNum, "approvedBy", currentManager);
            //some response needed?
        });

        app.post("/api/deny-request", ctx -> {
            String requestNum = ctx.body();
            String currentManager = ctx.sessionAttribute("currentUser");
            RequestsService rService = new RequestsService();
            rService.update("requestNumber", requestNum, "approvedBy", "Denied");
            //some response needed?
        });

        app.get("/profile", ctx -> {
            User currentUser;
            String u = "new username/email";
            UserService uServ;
            if(ctx.sessionAttribute("currentUserType") == "employee"){
                uServ = new EmployeeService();
                currentUser = uServ.read(ctx.sessionAttribute("currentUser"));
            } else if(ctx.sessionAttribute("currentUserType") == "manager"){
                uServ = new ManagerService();
                currentUser = uServ.read(ctx.sessionAttribute("currentUser"));
            }
            ctx.json(u);
            ctx.redirect("/user-profile.html");
        });

        /*
        app.post("/api/new-employee", ctx -> {
            ctx.formParam("newUser");
            ctx.formParam("newFirstName");
            ctx.formParam("newLastName");
            ctx.formParam("pass");
        });
        */

        /*
        app.post("/api/new-manager", ctx -> {
            String currentUser = ctx.sessionAttribute("currentUser");
            String newUser = ctx.formParam("newUser");
            String fname = ctx.formParam("newFirstName");
            String lname = ctx.formParam("newLastName");
            String pass = ctx.formParam("pass");
        })
        */
    }
}
