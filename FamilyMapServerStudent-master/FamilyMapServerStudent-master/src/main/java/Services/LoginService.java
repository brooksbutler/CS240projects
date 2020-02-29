package Services;
import DataAccessObjects.Database;
import Request.LoginRequest;
import Result.LoginResult;

/**
 *A Login Service object which generates an LoginResult object
 *<pre>
 *
 *</pre>
 */
public class LoginService {
    private Database myDB;

    /**
     * Public constructor
     */
    public LoginService(){
        myDB = new Database();
    }

    /**
     *
     * @param r, LoginRequest object
     * @return LoginResult object
     */
    public LoginResult login(LoginRequest r){
        return null;
    }
}
