package Services;

import DataAccessObjects.Database;
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
}
