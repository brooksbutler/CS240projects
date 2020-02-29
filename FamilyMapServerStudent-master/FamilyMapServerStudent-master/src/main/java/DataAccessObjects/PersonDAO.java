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
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists persons");
                stmt.executeUpdate("create table persons (personID VARCHAR(255) NOT NULL PRIMARY KEY,"+
                        " userName VARCHAR(255) NOT NULL, firstName VARCHAR(255) NOT NULL, lastName VARCHAR(255) NOT NULL, " +
                        "gender CHAR(255) NOT NULL, fatherID VARCHAR(255), motherID VARCHAR(255), spouseID VARCHAR(255),"+
                        " CONSTRAINT person_info UNIQUE (personID))");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("reset Person table failed");
        }
    }

    /**
     * Inserts a person into the person table
     * @param person
     * @throws Database.DatabaseException
     */
    public void insertPerson(PersonModel person) throws Database.DatabaseException {
        String sql = "INSERT INTO persons (personID, userName, firstName, lastName,"+
                " gender, fatherID, motherID, spouseID) values (?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,person.getPersonID());
            stmt.setString(2,person.getUserName());
            stmt.setString(3,person.getFirstName());
            stmt.setString(4,person.getLastName());
            if(person.getGender().length() != 1 || (!person.getGender().equals("m") && !person.getGender().equals("f"))){
                throw new Database.DatabaseException("Gender is incorrect format.");
            }
            stmt.setString(5,person.getGender());
            stmt.setString(6,person.getFatherID());
            stmt.setString(7,person.getMotherID());
            stmt.setString(8,person.getSpouseID());
            stmt.executeUpdate();
        }catch (SQLException e) {
            throw new Database.DatabaseException("Inserting person into database failed.");
        }
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
     * Return the person table as a string object
     * @return
     * @throws Database.DatabaseException
     */
    public String tableToString() throws Database.DatabaseException{
        return null;
    }

    /**
     * Get a single person model
     * @param personID
     * @return
     * @throws Database.DatabaseException
     */
    public PersonModel selectSinglePerson(String personID) throws Database.DatabaseException{
        PersonModel person = new PersonModel();
        ResultSet rs = null;
        String sql = "SELECT * FROM persons WHERE personID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person.setPersonID(rs.getString(1));
                person.setUserName(rs.getString(2));
                person.setFirstName(rs.getString(3));
                person.setLastName(rs.getString(4));
                person.setGender(rs.getString(5));
                person.setFatherID(rs.getString(6));
                person.setMotherID(rs.getString(7));
                person.setSpouseID(rs.getString(8));
                return person;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Database.DatabaseException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
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
