package Services;

import DataAccessObjects.Database;
import Result.PersonIDResult;
/**
 *An service object which generates an PersonIDResult object
 *<pre>
 *
 *</pre>
 */
public class PersonIDService {
    private Database myDB;

    /**
     * Public constructor
     */
    public PersonIDService(){
        myDB = new Database();
    }

    /**
     *
     * @param personId
     * @param authToken
     * @return PersonIDResult object
     */
    public PersonIDResult personID(String personId, String authToken){
        return null;
    }
}
