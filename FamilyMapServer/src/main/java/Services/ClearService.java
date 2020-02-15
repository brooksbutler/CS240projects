package Services;

import DataAccessObjects.Database;
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
}
