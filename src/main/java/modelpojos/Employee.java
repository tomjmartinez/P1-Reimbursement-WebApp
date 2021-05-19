package modelpojos;

import org.bson.types.ObjectId;

import java.util.Arrays;

/** pojo to represent employees mongo documents to act as an ORM to store and manipulate mongo documents
 * @author Tomas J. Martinez
 */
public class Employee extends User {

    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String[] requests;

    private ObjectId id;

    public Employee() {
    }

    public Employee(String email, String password, String firstname, String lastname, String[] requests, ObjectId id) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.requests = requests;
        this.id = id;
    }

    public Employee(String email, String password, String firstname, String lastname, String[] requests) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.requests = requests;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String[] getRequests() {
        return requests;
    }

    public void setRequests(String[] requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", requests=" + Arrays.toString(requests) +
                ", id=" + id +
                '}';
    }
}
