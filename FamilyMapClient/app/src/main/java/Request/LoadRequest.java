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

    public void setUsers(UserModel[] input) {
        users = new UserModel[input.length];
        System.arraycopy(input, 0, users, 0, input.length);
    }

    public PersonModel[] getPersons() {
        return persons;
    }

    public void setPersons(PersonModel[] input) {
        persons = new PersonModel[input.length];
        System.arraycopy(input, 0, persons, 0, input.length);
    }

    public EventModel[] getEvents() {
        return events;
    }

    public void setEvents(EventModel[] input) {
        events = new EventModel[input.length];
        System.arraycopy(input, 0, events, 0, input.length);
    }
}
