package DataAccessObjects;

import Model.AuthTokenModel;
import java.sql.*;


/**
 * DAO object that handles authenticator tokens and inserts them in the database
 */
public class AuthTokenDAO {
    private Connection conn;

    /**
     * Public constructor
     */
    public AuthTokenDAO(){
    }

    /**
     * Set connection to the database
     * @param c
     * @throws Database.DatabaseException
     */
    public void setConnection(Connection c) throws Database.DatabaseException{
        conn = c;
    }

    /**
     * Reset and clear the table
     * @throws Database.DatabaseException
     */
    public void resetTable() throws Database.DatabaseException {

    }

    /**
     * Insert a token into the database
     * @param auth
     * @throws Database.DatabaseException
     */
    public void insertTokenIntoTable(AuthTokenModel auth) throws Database.DatabaseException {

    }

    /**
     * Return a string object of the entire table
     * @return
     * @throws Database.DatabaseException
     */
    public String tableToString() throws Database.DatabaseException{
        return null;
    }

    /**
     * Check if the authenticator token exists
     * @param authToken
     * @return
     * @throws Database.DatabaseException
     */
    public boolean doesAuthTokenExist(String authToken) throws Database.DatabaseException {
        return false;
    }

    /**
     * Get the authenticator token model object
     * @param auth
     * @return
     * @throws Database.DatabaseException
     */
    public AuthTokenModel getAuthTokenModel(String auth) throws Database.DatabaseException {
        return null;
    }






}
