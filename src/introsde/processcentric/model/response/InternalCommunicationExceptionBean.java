package introsde.processcentric.model.response;

public class InternalCommunicationExceptionBean {
    private String message;
 
    public InternalCommunicationExceptionBean() {
    }
    public InternalCommunicationExceptionBean(String message) {
        this.message = message;
    }
 
    public String getMessage() {
        return message;
    }
 
}