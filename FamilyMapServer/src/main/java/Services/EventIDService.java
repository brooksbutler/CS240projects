package Services;

import DataAccessObjects.Database;
import Result.EventIDResult;

/**
 *An service object which generates an EventIDResult object
 *<pre>
 *
 *</pre>
 */
public class EventIDService {
    private Database myDB;

    /**
     * Public constructor
     */
    public EventIDService(){
        myDB = new Database();
    }

    /**
     *
     * @param eventId
     * @param authToken
     * @return EventIDResult object
     */
    public EventIDResult eventID(String eventId, String authToken){
        return null;
    }
}
