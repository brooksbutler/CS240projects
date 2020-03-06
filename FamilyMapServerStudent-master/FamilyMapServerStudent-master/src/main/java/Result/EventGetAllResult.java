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

    public void setData(EventIDResult[] input) {
        if (input == null){
            data = null;
        }
        else {
            data = new EventIDResult[input.length];
            for (int i = 0; i < input.length; i++) {
                data[i] = input[i];
            }
        }
//        System.arraycopy(input, 0, data, 0, input.length);
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

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }

        EventGetAllResult secondResult = (EventGetAllResult) o;

        if (success != secondResult.success) { return false; }
        if (message == null && secondResult.message != null) { return false; }
        if (message != null && secondResult.message == null){ return false; }
        if (message != null && secondResult.message != null){
            if (!message.equals(secondResult.message)){ return false; }
        }
        if (data!= null){
            for (int i = 0; i < data.length; i++){
                if (!data[i].equals(secondResult.data[i])){ return false; }
            }
        }
        return true;
    }
}
