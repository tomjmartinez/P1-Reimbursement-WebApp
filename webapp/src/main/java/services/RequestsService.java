package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dao.MongoDao;
import io.javalin.http.Context;
import modelpojos.MoneyRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

/** Service layer tasks to aid performing MoneyRequest tasks
 * @author Tomas J. Martinez
 */
public class RequestsService {
    MongoDatabase db;
    MongoCollection<MoneyRequest> mrCollection; // = MongoDao.database.getCollection("moneyrequests", MoneyRequest.class);
    private static final Logger logger = LogManager.getLogger(RequestsService.class);


    public RequestsService() {
        this.db = MongoDao.database;
    }

    public RequestsService(MongoDatabase db) {
        this.db = db;
    }

    /**
     * db crud method creates new MoneyRequest
     * @param currentUser
     * @param amount
     * @param reason
     * @param comments
     */
    public void create(String currentUser, Double amount, String reason, String comments) {
        refreshCollection();

        String randID = randAccountNum();

        MoneyRequest mr1 = new MoneyRequest(randID, currentUser, amount, reason, comments, "pending");
        System.out.println("from inside RequestService Create " + currentUser + " " + amount + " " + reason + " " + comments + " " + randID);


        Document document = new Document();
                document.append("requestNumber", randID)
                .append("employee", currentUser)
                .append("amount", amount)
                .append("reason", reason)
                .append("comments", comments)
                .append("approvedBy", "pending");


        mrCollection.insertOne(mr1);
        logger.info("creating new request in db" + currentUser + randID);
        //rCollection.insertOne(document);
        //MongoDao.database.getCollection("moneyrequests").insertOne(document);
    }

    /**
     * crud db method reads moneyrequest
     * @param email
     * @return moneyrequest found
     */
    public MoneyRequest read(String email) {
        refreshCollection();
        MoneyRequest mr = (MoneyRequest) mrCollection.find().filter(eq("employee", email)).first();
        logger.info("reading request in db" + email);
        return mr;
    }

    /**
     * crud method reads all moneyrequest for user passed
     * @param email
     * @return List of moneyrequest for user
     */
    public List<MoneyRequest> readAll(String email) {
        refreshCollection();
        System.out.println("current user email inside Request service" + email);
        FindIterable<MoneyRequest> all;
        if(email == "pending"){
            all = mrCollection.find().filter(eq("approvedBy", "pending"));
        } else {
            all = mrCollection.find().filter(eq("employee", email));
        }
        List<MoneyRequest> list = new ArrayList<>();
        for(MoneyRequest request: all) {
            list.add(request);
        }
        logger.info("reading all request in db");
        return list;
    }

    /**
     * crud method updates user at column
     * @param field
     * @param match
     * @param column
     * @param value
     */
    public void update(String field, String match, String column, String value) {
        refreshCollection();
        mrCollection.updateOne(
                eq(field, match),
                combine(set(column, value)));
        refreshCollection();
        logger.info("upadating requests in db" + match);
    }

    /*
    public void delete() {

    }
    */

    /**
     * gets fresh copy of db
     */
    public void refreshCollection(){
        mrCollection = db.getCollection("moneyrequests", MoneyRequest.class);
        logger.info("refreshing requests in db");
    }

    /**
     * method creates random number
     * @return random number for request ticket id
     */
    public String randAccountNum(){
        logger.info("random num being generated");
        return String.valueOf((int)Math.floor(Math.random() * (999999 - 100000)) + 100000);
    }
}
