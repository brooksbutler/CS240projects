package Services;

import DataAccessObjects.Database;
import Result.LoadResult;
import Request.LoadRequest;
/**
 *An service object which generates an LoadResult object
 *<pre>
 *
 *</pre>
 */
public class LoadService {
    private Database myDB;

    /**
     * Public constructor
     */
    public LoadService(){
        myDB = new Database();
    }

    /**
     *
     * @param r LoadRequest object
     * @return LoadResult object
     */
    public LoadResult load(LoadRequest r){
        return null;
    }

}
