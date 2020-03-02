package Services;

import DataAccessObjects.Database;
import DataAccessObjects.EventDAO;
import DataAccessObjects.PersonDAO;
import DataAccessObjects.UserDAO;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
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
        LoadResult response = new LoadResult();
        try{
            myDB.openConnection();
            myDB.resetTables();

            UserDAO myUserDAO = myDB.getMyUserDAO();
            PersonDAO myPersonDAO = myDB.getMyPersonDAO();
            EventDAO myEventDAO = myDB.getMyEventDAO();

            UserModel[] users = r.getUsers();
            PersonModel[] persons = r.getPersons();
            EventModel[] events = r.getEvents();

            for (UserModel user : users) {
                myUserDAO.insertUser(user);
            }

            for (PersonModel person : persons) {
                myPersonDAO.insertPerson(person);
            }

            for (EventModel event : events) {
                myEventDAO.insertEvent(event);
            }

            myDB.closeConnection(true);
            response.setSuccess(true);
            response.setNumUsers(users.length);
            response.setNumEvents(events.length);
            response.setNumPersons(persons.length);

        } catch (Database.DatabaseException e){
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            try{
                myDB.closeConnection(false);
            }catch (Database.DatabaseException d){
                response.setSuccess(false);
                response.setMessage(d.getMessage());
            }
        }
        return response;
    }
}
