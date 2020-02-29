package Result;

/**
 * A Result object for filling
 */
public class FillResult {
    private transient boolean success;
    private transient String message;
    private transient int numPersons = 0;
    private transient int numEvents = 0;


    /**
     * Public constructor
     */
    public FillResult(){
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
