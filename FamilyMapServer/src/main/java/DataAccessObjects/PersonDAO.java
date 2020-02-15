package DataAccessObjects;

import Model.PersonModel;
import Model.UserModel;

import java.sql.*;

/**
 * DAO object that handles persons and inserts them in the database
 */
public class PersonDAO {
    private Connection conn;
    private String[] femaleNames;
    private String[] maleNames;
    private String[] lastNames;

    /**
     * Public constructor
     */
    public PersonDAO(){

    }

    /**
     * Sets connection to database
     * @param c
     * @throws Database.DatabaseException
     */
    public void setConnection(Connection c) throws Database.DatabaseException{
        conn = c;
    }

    /**
     * Resets and clears the person table
     * @throws Database.DatabaseException
     */
    public void resetTable() throws Database.DatabaseException {

    }

    /**
     * Inserts a person into the person table
     * @param p
     * @throws Database.DatabaseException
     */
    public void insertPersonIntoDatabase(PersonModel p) throws Database.DatabaseException {

    }

    /**
     * Updates the parents of a person
     * @param p
     * @param parentID
     * @param parentGender
     * @throws Database.DatabaseException
     */
    public void updateParent(PersonModel p, String parentID, String parentGender) throws Database.DatabaseException{

    }

    /**
     * Updates spouse of a person
     * @param person
     * @param spouseID
     * @throws Database.DatabaseException
     */
    public void updateSpouse(PersonModel person, String spouseID) throws Database.DatabaseException{

    }

    /**
     * Generates a fake generation for a person
     * @param orphan
     * @param numGenerations
     * @param myEventDAO
     * @param orphanBirthYear
     * @throws Database.DatabaseException
     */
    public void generateGenerations(PersonModel orphan,
                                    int numGenerations,
                                    EventDAO myEventDAO,
                                    int orphanBirthYear) throws Database.DatabaseException {

    }

    /**
     * Make parents for a person
     * @param orphan
     * @param parentGender
     * @return
     * @throws Database.DatabaseException
     */
    public PersonModel makeParent(PersonModel orphan, String parentGender) throws Database.DatabaseException {
        return null;
    }

    /**
     * Select all persons in the person table
     * @param userName
     * @return
     * @throws Database.DatabaseException
     */
    public PersonModel[] selectAllPersons(String userName) throws Database.DatabaseException{
        return null;
    }

    /**
     * Return the person table as a string object
     * @return
     * @throws Database.DatabaseException
     */
    public String tableToString() throws Database.DatabaseException{
        return null;
    }

    /**
     * Deletes all persons associated with a specified user
     * @param u
     * @throws Database.DatabaseException
     */
    public void deleteAllPeopleOfUser(UserModel u) throws Database.DatabaseException {

    }

    /**
     * Get a single person model
     * @param personId
     * @return
     * @throws Database.DatabaseException
     */
    public PersonModel selectSinglePerson(String personId) throws Database.DatabaseException{
        return null;
    }

    /**
     * Check if a person in the the person table
     * @param personId
     * @return
     * @throws Database.DatabaseException
     */
    public boolean doesPersonExist(String personId) throws Database.DatabaseException{
        return false;
    }
}
