package Services;

import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.Database;
import DataAccessObjects.PersonDAO;
import Model.AuthTokenModel;
import Model.PersonModel;
import Result.PersonIDResult;
/**
 *An service object which generates an PersonIDResult object
 *<pre>
 *
 *</pre>
 */
public class PersonIDService {
    private Database myDB;

    /**
     * Public constructor
     */
    public PersonIDService(){
        myDB = new Database();
    }

    /**
     *
     * @param personId
     * @param authToken
     * @return PersonIDResult object
     */
    public PersonIDResult personID(String personId, String authToken){
        PersonIDResult myResponse = new PersonIDResult();

        try{
            myDB.openConnection();
            PersonDAO myPersonDAO = myDB.getMyPersonDAO();
            AuthTokenDAO myAuthDAO = myDB.getMyAuthTokenDAO();

            if(myAuthDAO.doesAuthTokenExist(authToken)){
                AuthTokenModel auth = myAuthDAO.getAuthTokenModel(authToken);
                if (!personId.equals(auth.getPersonID())){
                    throw new Database.DatabaseException("PersonID does not match given authToken");
                }
                if (myPersonDAO.doesPersonExist(personId)){
                    PersonModel out = myPersonDAO.selectSinglePerson(personId);
                    myResponse = new PersonIDResult(out);
                }
            }

            myResponse.setSuccess(true);
            myDB.closeConnection(true);
            myResponse.setSuccess(true);

        } catch (Database.DatabaseException e){
            myResponse.setSuccess(false);
            myResponse.setMessage(e.getMessage());
            try{
                myDB.closeConnection(false);
            }catch (Database.DatabaseException d){
                myResponse.setSuccess(false);
                myResponse.setMessage(d.getMessage());
            }
        }
        return myResponse;
    }
}
