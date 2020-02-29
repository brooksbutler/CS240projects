package Services;

import DataAccessObjects.Database;
import DataAccessObjects.EventDAO;
import Model.PersonModel;
import Result.FillResult;

/**
 *A service object which generates an FillResult object
 *<pre>
 *
 *</pre>
 */
public class FillService {
    private Database myDB;

    /**
     * Public constructor
     */
    public FillService(){
        myDB = new Database();
    }

    /**
     *
     * @param userName
     * @param numGenerations
     * @return FillResult object
     */
    public FillResult fill(String userName, int numGenerations){
        return null;
    }

    /**
     * Makes rot events for a person
     * @param root
     * @return
     * @throws Database.DatabaseException
     */
    public int makeRootsEvents(PersonModel root) throws Database.DatabaseException {
        return 0;
    }

    /**
     * Generates event data on parents for a user
     * @param mother
     * @param father
     * @param orphanBirthYear
     * @return
     * @throws Database.DatabaseException
     */
    public int generateEventDataParents(PersonModel mother,
                                        PersonModel father,
                                        int orphanBirthYear) throws Database.DatabaseException {
        return 0;
    }

    /**
     * Generates a fake generation for a person
     * @param orphan
     * @param numGenerations
     * @param myEventDAO
     * @param orphanBirthYear
     * @throws Database.DatabaseException
     */
    public void generateGenerations(PersonModel orphan,
                                    int numGenerations,
                                    EventDAO myEventDAO,
                                    int orphanBirthYear) throws Database.DatabaseException {

    }
}
