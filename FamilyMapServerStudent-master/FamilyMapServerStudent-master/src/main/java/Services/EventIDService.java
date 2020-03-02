package Services;

import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.Database;
import DataAccessObjects.EventDAO;
import Model.AuthTokenModel;
import Model.EventModel;
import Result.EventIDResult;

/**
 *An service object which generates an EventIDResult object
 *<pre>
 *
 *</pre>
 */
public class EventIDService {
    private Database myDB;

    /**
     * Public constructor
     */
    public EventIDService(){
        myDB = new Database();
    }

    /**
     *
     * @param eventId
     * @param authToken
     * @return EventIDResult object
     */
    public EventIDResult eventID(String eventId, String authToken){
        EventIDResult myResult = new EventIDResult();

        try{
            myDB.openConnection();
            EventDAO myEventDAO = myDB.getMyEventDAO();
            AuthTokenDAO myAuthDAO = myDB.getMyAuthTokenDAO();

            if(myAuthDAO.doesAuthTokenExist(authToken)){
                AuthTokenModel auth = myAuthDAO.getAuthTokenModel(authToken);
                if (myEventDAO.doesEventExist(eventId)){
                    EventModel event = myEventDAO.selectSingleEvent(eventId);
                    if (!event.geUserName().equals(auth.getUserName())){
                        throw new Database.DatabaseException("Descendant of event and username of auth token do not match");
                    }
                    myResult = new EventIDResult(event);
                }
            }
            myDB.closeConnection(true);
            myResult.setSuccess(true);

        } catch (Database.DatabaseException e){
            myResult.setSuccess(false);
            myResult.setMessage(e.getMessage());
            try{
                myDB.closeConnection(false);
            }catch (Database.DatabaseException d){
                myResult.setSuccess(false);
                myResult.setMessage(d.getMessage());
            }
        }
        return myResult;
    }
}
