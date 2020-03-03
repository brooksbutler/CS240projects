package Services;

import DataAccessObjects.*;
import Model.AuthTokenModel;
import Model.PersonModel;
import Model.UserModel;
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
        RegisterResult result = new RegisterResult();
        FillService fill = new FillService();

        try{
            myDB.openConnection();

            UserDAO myUserDAO = myDB.getMyUserDAO();
            PersonDAO myPersonDAO = myDB.getMyPersonDAO();
            EventDAO myEventDAO = myDB.getMyEventDAO();
            AuthTokenDAO myAuthDAO = myDB.getMyAuthTokenDAO();

            UserModel u = new UserModel(r);
            myUserDAO.insertUser(u);

            PersonModel root = new PersonModel(u);

            myPersonDAO.insertPerson(root); //inserts root into database

            int rootBirthYear = fill.makeRootsEvents(root, myEventDAO); //make root's events

            //Now were going to give generations root, which generates fathers and mothers, then generates fathers and mothers events, and each father and mother is passed on to have its generations made
            fill.generateGenerations(root, 4, myEventDAO, myPersonDAO, rootBirthYear);

            //Auth token stuff
            AuthTokenModel returnAuth = new AuthTokenModel(u);
            myAuthDAO.insertToken(returnAuth);
            result = new RegisterResult(returnAuth);
            result.setSuccess(true);

            myDB.closeConnection(true);

        } catch (Database.DatabaseException e){
            result.setSuccess(false);
            result.setMessage("User already exists failed");

            try{
                myDB.closeConnection(false);
            }catch (Database.DatabaseException d){
                result.setSuccess(false);
                result.setMessage(d.getMessage());
            }
        }
        return result;
    }
}
