package modelpojos;

import org.bson.types.ObjectId;

/** Simple pojo to represent accounts mongo documents to act as an ORM to store and manipulate mongo documents
 * @author Tomas J. Martinez
 */
public class MoneyRequest {
    private String requestNumber;
    private String employee; //later can implement employeeID
    private Double amount;
    private String reason; //dropdown box
    private String comments;
    private String approvedBy;
    private ObjectId id;
    //TODO can later add a placeholder array to attach supporting documents, like receipts


    public MoneyRequest() {
    }

    public MoneyRequest(String requestNumber, String employee, Double amount, String reason, String comments, String approvedBy, ObjectId id) {
        this.requestNumber = requestNumber;
        this.employee = employee;
        this.amount = amount;
        this.reason = reason;
        this.comments = comments;
        this.approvedBy = approvedBy;
        this.id = id;
    }

    public MoneyRequest(String requestNumber, String employee, Double amount, String reason, String comments, String approvedBy) {
        this.requestNumber = requestNumber;
        this.employee = employee;
        this.amount = amount;
        this.reason = reason;
        this.comments = comments;
        this.approvedBy = approvedBy;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MoneyRequest{" +
                "requestNumber='" + requestNumber + '\'' +
                ", employee='" + employee + '\'' +
                ", amount=" + amount +
                ", reason='" + reason + '\'' +
                ", comments='" + comments + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                ", id=" + id +
                '}';
    }
}
