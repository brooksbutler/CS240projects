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
        private String message;

        public DatabaseException(){
            message = new String();
        }
        public DatabaseException(String message){
            this.message = message;
        }
        public String getMessage(){
            return message;
        }
    }

    public Connection getConn() {
        return conn;
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
        try {
            final String CONNECTION_URL = "jdbc:sqlite:database.sqlite";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            //Sets connections for DAOs
            myUserDAO.setConnection(conn);
            myEventDAO.setConnection(conn);
            myPersonDAO.setConnection(conn);
            myAuthTokenDAO.setConnection(conn);

            // Start a transaction
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new DatabaseException("Opening connection failed");
        }
    }

    /**
     * Resets and clears all tables
     * @throws DatabaseException
     */
    public void resetTables() throws DatabaseException {
        myUserDAO.resetTable();
        myPersonDAO.resetTable();
        myEventDAO.resetTable();
        myAuthTokenDAO.resetTable();
    }

    /**
     * Deletes everything relating to a specific user
     * @param u UserModel object
     * @throws DatabaseException
     */
    public void deleteEverythingOfUser(UserModel u) throws DatabaseException { //does not make new usermodel of username given
        myUserDAO.deleteUser(u);
        myPersonDAO.deleteAllPeopleOfUser(u);
        myEventDAO.deleteAllEventsOfUser(u);
    }

    /**
     * Closes database connection
     * @param commit
     * @throws DatabaseException
     */
    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }
            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            throw new DatabaseException("closeConnection failed");
        }
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
