package Model;
/**
 * Model object for events
 */
public class EventModel {

    private String eventID;
    private String associatedUsername;
    private String personID;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    /**
     * Default constructor
     */
    public EventModel(){
    }
    public EventModel(String eventID, String userName, String personID, Double latitude, Double longitude,
                      String country,String city, String eventType, int year){
        this.eventID = eventID;
        this.associatedUsername = userName;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String geUserName() {
        return associatedUsername;
    }

    public void setUserName(String userName) {
        this.associatedUsername = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }
        EventModel secondEvent = (EventModel) o;

        if (!eventID.equals(secondEvent.eventID)) { return false; }
        if (!associatedUsername.equals(secondEvent.associatedUsername)) { return false; }
        if (!personID.equals(secondEvent.personID)) { return false; }
        if (!latitude.equals(secondEvent.latitude)) { return false; }
        if (!longitude.equals(secondEvent.longitude)) { return false; }
        if (!country.equals(secondEvent.country)) { return false; }
        if (!city.equals(secondEvent.city)) { return false; }
        if (!eventType.equals(secondEvent.eventType)) { return false; }
        if (year != secondEvent.year){ return false; }
        return true;
    }
}
