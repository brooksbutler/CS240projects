package Services;
import DataAccessObjects.Database;
import Result.PersonGetAllResult;
/**
 *An service object which generates an PersonGetAllResult object
 *<pre>
 *
 *</pre>
 */
public class PersonGetAllService {
    private Database myDB;

    /**
     * Public constructor
     */
    public PersonGetAllService(){
        myDB = new Database();
    }

    /**
     *
     * @param authToken
     * @return PersonGetAllResult object
     */
    public PersonGetAllResult personGetAll(String authToken){
        return null;
    }
}
