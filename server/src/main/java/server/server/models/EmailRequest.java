package server.server.models;

public class EmailRequest {
    
    private String recipient;
    private String requestor;
    private String listingName;
    
    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public String getRequestor() {
        return requestor;
    }
    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }
    public String getListingName() {
        return listingName;
    }
    public void setListingName(String listingName) {
        this.listingName = listingName;
    }
    public EmailRequest() {
    }
    public EmailRequest(String recipient, String requestor, String listingName) {
        this.recipient = recipient;
        this.requestor = requestor;
        this.listingName = listingName;
    }
}
