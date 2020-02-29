package Result;

import Model.EventModel;
/**
 * A Result object for getting an event ID
 */
public class EventIDResult {
    private String descendant;
    private String eventID;
    private String personID;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;
    private transient boolean success;
    private transient String message;


    /**
     * Default constructor
     */
    public EventIDResult(){
    }

    /**
     * Public constructor fron an event model
     * @param e
     */
    public EventIDResult(EventModel e){
    }

    public void setSuccess(boolean b){
        success = b;
    }

    public void setMessage(String s){
        message = s;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
