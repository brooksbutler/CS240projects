package Result;

import Model.EventModel;

/**
 * A Result object for getting all events
 */
public class EventGetAllResult {
    private EventIDResult[] data;
    private transient boolean success;
    private transient String message;

    /**
     * Public constructor
     */
    public EventGetAllResult(){
    }

    public EventIDResult[] getData() {
        return data;
    }

    public void setData(EventIDResult[] data) {
        this.data = data;
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
}
