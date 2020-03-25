package Services;
import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.Database;
import DataAccessObjects.UserDAO;
import Model.AuthTokenModel;
import Model.UserModel;
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
        LoginResult loginResponse = new LoginResult();
        UserDAO myUserDAO = myDB.getMyUserDAO();
        AuthTokenDAO myAuthDAO = myDB.getMyAuthTokenDAO();

        try{
            myDB.openConnection();
            UserModel user = new UserModel(r);

            if (myUserDAO.doUsernameAndPasswordExist(user)) { //yes, the username and password are valid
                AuthTokenModel returnAuth = new AuthTokenModel(user);
                returnAuth.setPersonID(myUserDAO.getPersonIDOfUser(user)); // will fill in the personID of the user
                if(!myAuthDAO.doesPersonExist(returnAuth.getPersonID())){
                    myAuthDAO.insertToken(returnAuth);
                }
                else {
                    myAuthDAO.updateAuthToken(returnAuth,returnAuth.getAuthToken());
                }


                loginResponse = new LoginResult(returnAuth);
                loginResponse.setSuccess(true);

                myDB.closeConnection(true);
            }

        } catch (Database.DatabaseException d){
            loginResponse.setMessage(d.getMessage());
            loginResponse.setSuccess(false);
            try{
                myDB.closeConnection(false);
            }catch (Database.DatabaseException e){
                loginResponse.setSuccess(false);
                loginResponse.setMessage(e.getMessage());
            }
        }
        return loginResponse;
    }
}
