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
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
