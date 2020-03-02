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
        ClearResult result = new ClearResult();
        try{
            myDB.openConnection();
            myDB.resetTables();

            myDB.closeConnection(true);

        } catch (Database.DatabaseException e){
            System.out.println(e.getMessage());
            result.setMessage("Internal server error");

            try{
                myDB.closeConnection(false);
            }catch (Database.DatabaseException d){
                result.setMessage(d.getMessage());
                return result;
            }

            return result;
        }
        result.setMessage("Clear succeeded");
        return result;
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
