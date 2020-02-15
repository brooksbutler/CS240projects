package Services;

import DataAccessObjects.Database;
import Result.EventGetAllResult;
/**
 *An service object which generates an EventGetAllResult object
 *<pre>
 *
 *</pre>
 */
public class EventGetAllService {
    private Database myDB;

    /**
     * Public constructor
     */
    public EventGetAllService(){
        myDB = new Database();
    }

    /**
     * Function that takes an authenticator token and returns an EventGetAllResult
     * @param authToken
     * @return EventGetAllResult object
     */
    public EventGetAllResult eventGetAll(String authToken){
        return null;
    }
}
