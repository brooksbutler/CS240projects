package DataAccessObjects;

import Model.UserModel;
import java.util.UUID;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * DAO object that handles users and inserts them in the database
 */
public class UserDAO {

    private Connection conn;

    /**
     * Public constructor
     */
    public UserDAO() {}

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
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists users");
                stmt.executeUpdate("create table users (userName VARCHAR(255) NOT NULL PRIMARY KEY, "+
                        "password VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, firstName VARCHAR(255) NOT NULL, " +
                        "lastName VARCHAR(255) NOT NULL, gender CHAR(255) NOT NULL, personID VARCHAR(255) NOT NULL,"+
                        " CONSTRAINT user_info UNIQUE (userName))");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("reset UserTable failed");
        }
    }

    /**
     * Inserts a user into the database
     * @param user
     * @throws Database.DatabaseException
     */
    public void insertUser(UserModel user) throws Database.DatabaseException {
        String sql = "insert into users (userName, password, email, firstName, lastName, gender, personID)"+
                " values (?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,user.getUserName());
            stmt.setString(2,user.getPassword());
            stmt.setString(3,user.getEmail());
            stmt.setString(4,user.getFirstName());
            stmt.setString(5,user.getLastName());

            if(user.getGender().length() != 1 || (!user.getGender().equals("m") && !user.getGender().equals("f"))){
                throw new Database.DatabaseException("gender is incorrect format user");
            }
            stmt.setString(6,user.getGender());
            stmt.setString(7,user.getPersonID());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new Database.DatabaseException("Error encountered while inserting into the database");
        }
    }

    /**
     * Check if a username is in the database
     * @param u
     * @return
     * @throws Database.DatabaseException
     */
    public boolean doesUserNameExist(String u) throws Database.DatabaseException {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from users WHERE userName = '" + u + "'";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                if (!rs.next() ) {
                    throw new Database.DatabaseException("no such username");
                } else {
                    return true;
                }
            }
            finally {
                if (rs != null) { rs.close(); }
                if (stmt != null) { stmt.close(); }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("no such username");
        }
    }


    /**
     * Get a single user model
     * @param userID
     * @return
     * @throws Database.DatabaseException
     */
    public UserModel getUserModel(String userID) throws Database.DatabaseException {
        UserModel user = new UserModel();
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE userName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user.setUserName(rs.getString(1));
                user.setPassword(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setFirstName(rs.getString(4));
                user.setLastName(rs.getString(5));
                user.setGender(rs.getString(6));
                user.setPersonID(rs.getString(7));
                return user;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Database.DatabaseException("Error encountered while finding event");
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
     * Check if both the username and password exist in the database
     * @param u
     * @return
     * @throws Database.DatabaseException
     */
    public boolean doUsernameAndPasswordExist(UserModel u) throws Database.DatabaseException{
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from users WHERE userName = '" + u.getUserName() +
                        "' AND password = '" + u.getPassword() + "'";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                if (!rs.next() ) {
                    throw new Database.DatabaseException("no such username and/or password");
                } else {
                    return true;
                }
            }
            finally {
                if (rs != null) { rs.close(); }
                if (stmt != null) { stmt.close(); }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("no such username and/or password");
        }
    }

    /**
     * Get the personID of the user
     * @param u
     * @return
     * @throws Database.DatabaseException
     */
    public String getPersonIDOfUser(UserModel u) throws Database.DatabaseException {
        String personID = new String();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from users WHERE userName = '" + u.getUserName() + "' AND password = '" + u.getPassword() + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    personID = rs.getString(7);
                }
            }
            finally {
                if (rs != null) { rs.close(); }
                if (stmt != null) { stmt.close(); }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("getPersonIDOfUser failed");
        }
        return personID;
    }

    /**
     * Returns the user table as a string object
     * @return
     * @throws Database.DatabaseException
     */
    public String tableToString() throws Database.DatabaseException{
        StringBuilder out = new StringBuilder();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from users";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String word = rs.getString(1);
                    String password = rs.getString(2);
                    String email = rs.getString(3);
                    String firstName = rs.getString(4);
                    String lastName = rs.getString(5);
                    String gender = rs.getString(6);
                    String personID = rs.getString(7);
                    out.append((word + "\t" + password + "\t" + email + "\t" + firstName +
                            "\t" + lastName + "\t" + gender + "\t" + personID + "\n"));
                }
            }
            finally {
                if (rs != null) { rs.close(); }
                if (stmt != null) { stmt.close(); }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("seeTable users failed");
        }
        return out.toString();
    }

    public void deleteUser(UserModel u) throws Database.DatabaseException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM users WHERE userName = '" + u.getUserName() + "'");
            }
            finally {
                if (stmt != null) { stmt.close(); }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("delete User failed");
        }
    }
}
