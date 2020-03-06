package Services;

import DataAccessObjects.*;
import Model.AuthTokenModel;
import Model.EventModel;
import Result.EventGetAllResult;
import Result.EventIDResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *An service object which generates an EventGetAllResult object
 *<pre>
 *
 *</pre>
 */
public class EventGetAllService {
    private Database myDB;

    /**
     * Public constructor
     */
    public EventGetAllService(){
        myDB = new Database();
    }

    /**
     * Function that takes an authenticator token and returns an EventGetAllResult
     * @param authToken
     * @return EventGetAllResult object
     */
    public EventGetAllResult eventGetAll(String authToken){
        EventGetAllResult myResult = new EventGetAllResult();
        try{
            myDB.openConnection();
            EventDAO myEventDAO = myDB.getMyEventDAO();
            AuthTokenDAO myAuthDAO = myDB.getMyAuthTokenDAO();


            if(myAuthDAO.doesAuthTokenExist(authToken)){
                AuthTokenModel auth = myAuthDAO.getAuthTokenModel(authToken);
                myResult.setData(selectAllEvents(auth.getUserName(), myDB.getConn()));
                myDB.closeConnection(true);
                myResult.setSuccess(true);
            } else {
                myResult.setSuccess(false);
                myDB.closeConnection(false);
            }



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

    /**
     * Get all events
     * @param userName
     * @return
     * @throws Database.DatabaseException
     */
    public EventIDResult[] selectAllEvents(String userName, Connection conn) throws Database.DatabaseException{
        ArrayList<EventIDResult> outArray = new ArrayList<EventIDResult>();

        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from events WHERE userName = '" + userName + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    EventIDResult out = new EventIDResult();
                    out.setEventID(rs.getString(1));
                    out.setUserName(rs.getString(2));
                    out.setPersonID(rs.getString(3));
                    out.setLatitude(rs.getDouble(4));
                    out.setLongitude(rs.getDouble(5));
                    out.setCountry(rs.getString(6));
                    out.setCity(rs.getString(7));
                    out.setEventType(rs.getString(8));
                    out.setYear(rs.getInt(9));
                    outArray.add(out);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("error: Select all events failed");
        }
        EventIDResult[] outFinal = new EventIDResult[outArray.size()];
        outFinal = outArray.toArray(outFinal);
        return outFinal;
    }
}
