package Result;

import Model.EventModel;
/**
 * A Result object for getting an event ID
 */
public class EventIDResult {
    private String associatedUsername;
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
        this.associatedUsername = e.geUserName();
        this.eventID = e.getEventID();
        this.personID = e.getPersonID();
        this.latitude = e.getLatitude();
        this.longitude = e.getLongitude();
        this.country = e.getCountry();
        this.city = e.getCity();
        this.eventType = e.getEventType();
        this.year = e.getYear();
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

    public void setUserName(String userName) { this.associatedUsername = userName; }

    public void setEventID(String eventID) { this.eventID = eventID; }

    public void setPersonID(String personID) { this.personID = personID; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public void setCountry(String country) { this.country = country; }

    public void setCity(String city) { this.city = city; }

    public void setEventType(String eventType) { this.eventType = eventType; }

    public void setYear(int year) { this.year = year; }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }

        EventIDResult secondResponse = (EventIDResult) o;

        if (eventID!= null && !eventID.equals(secondResponse.eventID)) { return false; }
        if (associatedUsername != null && !associatedUsername.equals(secondResponse.associatedUsername)) { return false; }
        if (personID != null && !personID.equals(secondResponse.personID)) { return false; }
        if (latitude != null && !latitude.equals(secondResponse.latitude)) { return false; }
        if (longitude != null && !longitude.equals(secondResponse.longitude)) { return false; }
        if (country != null && !country.equals(secondResponse.country)) { return false; }
        if (city != null && !city.equals(secondResponse.city)) { return false; }
        if (eventType != null && !eventType.equals(secondResponse.eventType)) { return false; }
        if (year != secondResponse.year){ return false; }
        if (success != secondResponse.success) { return false; }
        if (message == null && secondResponse.message != null) { return false; }
        if (message != null && secondResponse.message == null){ return false; }
        if (message != null && secondResponse.message != null){
            if (!message.equals(secondResponse.message)){ return false; }
        }
        return true;
    }
}
