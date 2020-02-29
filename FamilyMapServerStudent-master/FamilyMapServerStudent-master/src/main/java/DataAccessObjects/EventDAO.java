package DataAccessObjects;

import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;

import java.sql.*;

/**
 * DAO object that handles events and inserts them in the database
 */
public class EventDAO {

    private Connection conn;

    /**
     * Public constructor
     */
    public EventDAO(){
    }

    /**
     * sets conention to the database
     * @param c
     * @throws Database.DatabaseException
     */
    public void setConnection(Connection c) throws Database.DatabaseException{
        conn = c;
    }

    /**
     * Resets and clears the table
     * @throws Database.DatabaseException
     */
    public void resetTable() throws Database.DatabaseException {

    }

    /**
     * Insterts event into database
     * @param e
     * @throws Database.DatabaseException
     */
    public void insertEventIntoDatabase(EventModel e) throws Database.DatabaseException {

    }

    /**
     * Gets a single event based on an event ID
     * @param eventId
     * @return
     * @throws Database.DatabaseException
     */
    public EventModel selectSingleEvent(String eventId) throws Database.DatabaseException {
        return null;
    }

    /**
     * Check if an event exists
     * @param eventId
     * @return
     * @throws Database.DatabaseException
     */
    public boolean doesEventExist(String eventId) throws Database.DatabaseException{
        return false;
    }


    /**
     * Return the event table as a string object
     * @return
     * @throws Database.DatabaseException
     */
    public String tableToString() throws Database.DatabaseException {
        return null;
    }


}
