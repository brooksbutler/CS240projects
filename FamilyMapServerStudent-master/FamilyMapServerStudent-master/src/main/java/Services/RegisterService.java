package Services;

import DataAccessObjects.*;
import Result.RegisterResult;
import Request.RegisterRequest;
/**
 *A service object which generates an RegisterResult object
 *<pre>
 *
 *</pre>
 */
public class RegisterService {
    private Database myDB;

    /**
     * Public constructor
     */
    public RegisterService(){
        myDB = new Database();
    }

    /**
     *
     * @param r RegisterRequest
     * @return RegisterResult object
     */
    public RegisterResult register(RegisterRequest r){
        return null;
    }
}
