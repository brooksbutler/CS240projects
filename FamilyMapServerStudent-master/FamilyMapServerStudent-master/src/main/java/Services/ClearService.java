package Services;

import DataAccessObjects.Database;
import Model.UserModel;
import Result.ClearResult;

/**
 *A Clear Service object which generates a clear result object
 *<pre>
 *
 *</pre>
 */
public class ClearService {
    private Database myDB;

    /**
     * Constructor for ClearService object which populates
     */
    public ClearService(){
        myDB = new Database();
    }

    /**
     * Function that returns a ClearResult object
     * @return ClearResult object
     */
    public ClearResult clear(){
        return null;
    }

    /**
     * Deletes all events relating to a specific user
     * @param u
     * @throws Database.DatabaseException
     */
    public void deleteAllEventsOfUser(UserModel u) throws Database.DatabaseException {

    }

    /**
     * Deletes all persons associated with a specified user
     * @param u
     * @throws Database.DatabaseException
     */
    public void deleteAllPeopleOfUser(UserModel u) throws Database.DatabaseException {

    }

    public void deleteUser(UserModel u) throws Database.DatabaseException {

    }
}
