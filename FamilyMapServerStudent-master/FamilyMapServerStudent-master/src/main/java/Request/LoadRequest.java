package Request;

import Model.UserModel;
import Model.PersonModel;
import Model.EventModel;

/**
 * Request object for load requests
 */
public class LoadRequest {

    private UserModel[] users;
    private PersonModel[] persons;
    private EventModel[] events;

    /**
     * Public constructor
     */
    public LoadRequest(){
        //To be implemented later
    }

    public UserModel[] getUsers() {
        return users;
    }

    public void setUsers(UserModel[] users) {
        this.users = users;
    }

    public PersonModel[] getPersons() {
        return persons;
    }

    public void setPersons(PersonModel[] persons) {
        this.persons = persons;
    }

    public EventModel[] getEvents() {
        return events;
    }

    public void setEvents(EventModel[] events) {
        this.events = events;
    }
}
