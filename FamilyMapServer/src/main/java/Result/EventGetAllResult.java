package Result;

import Model.EventModel;

/**
 * A Result object for getting all events
 */
public class EventGetAllResult {
    private EventModel[] data;
    private transient boolean success;
    private transient String message;

    /**
     * Public constructor
     */
    public EventGetAllResult(){
    }

    public EventModel[] getData() {
        return data;
    }

    public void setData(EventModel[] data) {
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
