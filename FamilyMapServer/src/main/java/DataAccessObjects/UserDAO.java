package DataAccessObjects;

import Model.UserModel;
import java.sql.*;

/**
 * DAO object that handles users and inserts them in the database
 */
public class UserDAO {

    private Connection conn;

    /**
     * Public constructor
     */
    public UserDAO()  {
    }

    /**
     * Sets connection to the database
     * @param c
     * @throws Database.DatabaseException
     */
    public void setConnection(Connection c) throws Database.DatabaseException{
        conn = c;
    }

    /**
     * Resets and clears the user table
     * @throws Database.DatabaseException
     */
    public void resetTable() throws Database.DatabaseException {

    }

    /**
     * Inserts a user into the database
     * @param u
     * @throws Database.DatabaseException
     */
    public void insertUserIntoDatabase(UserModel u) throws Database.DatabaseException {

    }

    /**
     * Returns the user table as a string object
     * @return
     * @throws Database.DatabaseException
     */
    public String tableToString() throws Database.DatabaseException{
        return null;
    }

    /**
     * Check if a username is in the database
     * @param u
     * @return
     * @throws Database.DatabaseException
     */
    public boolean doesUserNameExist(String u) throws Database.DatabaseException {
        return false;
    }

    /**
     * Remove a user
     * @param u
     * @throws Database.DatabaseException
     */
    public void deleteUser(UserModel u) throws Database.DatabaseException {

    }

    /**
     * Get a single user model
     * @param u
     * @return
     * @throws Database.DatabaseException
     */
    public UserModel getUserModel(String u) throws Database.DatabaseException {
        return null;
    }

    /**
     * Check if both the username and password exist in the database
     * @param u
     * @return
     * @throws Database.DatabaseException
     */
    public boolean doUsernameAndPasswordExist(UserModel u) throws Database.DatabaseException{
        return false;
    }

    /**
     * Get the personID of the user
     * @param u
     * @return
     * @throws Database.DatabaseException
     */
    public String getPersonIDOfUser(UserModel u) throws Database.DatabaseException {
        return null;
    }
}
