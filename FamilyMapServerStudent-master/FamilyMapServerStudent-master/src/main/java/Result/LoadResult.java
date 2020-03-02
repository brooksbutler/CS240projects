package Result;
/**
 * A Result object for loading
 */
public class LoadResult {
    private boolean success;
    private String message;
    private int numUsers;
    private int numPersons;
    private int numEvents;

    /**
     * Public constructor
     */
    public LoadResult(){
        message = "";
        numUsers = 0;
        numPersons = 0;
        numEvents = 0;
        success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNumUsers() {
        return numUsers;
    }

    public void setNumUsers(int numUsers) {
        this.numUsers = numUsers;
    }

    public int getNumPersons() {
        return numPersons;
    }

    public void setNumPersons(int numPersons) {
        this.numPersons = numPersons;
    }

    public int getNumEvents() {
        return numEvents;
    }

    public void setNumEvents(int numEvents) {
        this.numEvents = numEvents;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }

        LoadResult secondResponse = (LoadResult) o;

        if(numEvents != secondResponse.numEvents){ return false; }
        if(numPersons != secondResponse.numPersons){ return false; }
        if(numUsers != secondResponse.numUsers){ return false; }
        if(success != secondResponse.success) { return false; }
        if(message == null && secondResponse.message != null) { return false; }
        if(message != null && secondResponse.message == null){ return false; }
        if(message != null && secondResponse.message != null){
            if (!message.equals(secondResponse.message)){ return false; }
        }
        return true;
    }
}
