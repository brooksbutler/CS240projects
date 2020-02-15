package DataAccessObjects;

import java.sql.*;
import Model.UserModel;

/**
 *A Database class which receives a connection from the client and uses this connection for multiple purposes,
 * this is done in order to use one connection per client
 *<pre>
 *<b>Domain</b>:
 *     connection : string
 *     myUserDAO : UserDAO
 *     myPersonDAO : PersonDAO
 *     myEventDAO : EventDAO
 *     myAuthTokenDAO : AuthTokenDAO
 *
 *</pre>
 */
public class Database {

    /**
     * An exception class which is thrown if any error occurs in the DAO methods
     */
    public static class DatabaseException extends Exception {

    }

    private UserDAO myUserDAO;
    private PersonDAO myPersonDAO;
    private EventDAO myEventDAO;
    private AuthTokenDAO myAuthTokenDAO;
    private Connection conn;

    /**
     * Public constructor
     */
    public Database(){
        myUserDAO = new UserDAO();
        myPersonDAO = new PersonDAO();
        myEventDAO = new EventDAO();
        myAuthTokenDAO = new AuthTokenDAO();
    }

    /**
     * Opens sequel connection
     * @throws DatabaseException
     */
    public void openConnection() throws DatabaseException {

    }

    /**
     * Resets and clears all tables
     * @throws DatabaseException
     */
    public void resetTables() throws DatabaseException {

    }

    /**
     * Deletes everything relating to a specific user
     * @param u UserModel object
     * @throws DatabaseException
     */
    public void deleteEverythingOfUser(UserModel u) throws DatabaseException {

    }

    /**
     * Closes database connection
     * @param commit
     * @throws DatabaseException
     */
    public void closeConnection(boolean commit) throws DatabaseException {

    }

    public UserDAO getMyUserDAO() {
        return myUserDAO;
    }

    public PersonDAO getMyPersonDAO() {
        return myPersonDAO;
    }

    public EventDAO getMyEventDAO() {
        return myEventDAO;
    }

    public AuthTokenDAO getMyAuthTokenDAO() {
        return myAuthTokenDAO;
    }
}
