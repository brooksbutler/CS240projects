package DataAccessObjects;

import Model.AuthTokenModel;
import Model.EventModel;

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
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists authTokens");
                stmt.executeUpdate("create table authTokens (authToken VARCHAR (255) NOT NULL UNIQUE," +
                        "userName VARCHAR (255) NOT NULL UNIQUE, personID VARCHAR (255) NOT NULL UNIQUE,"+
                        " CONSTRAINT authToken_info UNIQUE (authToken))");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("reset authToken table failed");
        }
    }

    /**
     * Insert a token into the database
     * @param auth
     * @throws Database.DatabaseException
     */
    public void insertToken(AuthTokenModel auth) throws Database.DatabaseException {
        String sql = "insert into authTokens (authToken, userName, personID)"+
                " values (?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,auth.getAuthToken());
            stmt.setString(2,auth.getUserName());
            stmt.setString(3,auth.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Database.DatabaseException("Error encountered while inserting authToken into the database");
        }
    }
    public AuthTokenModel getAuthTokenModel(String authToken) throws Database.DatabaseException {
        AuthTokenModel authTokenModel = new AuthTokenModel();
        ResultSet rs = null;
        String sql = "SELECT * FROM authTokens WHERE authToken = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authTokenModel.setAuthToken(rs.getString(1));
                authTokenModel.setUserName(rs.getString(2));
                authTokenModel.setPersonID(rs.getString(3));
                return authTokenModel;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Database.DatabaseException("Error encountered while finding authToken");
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
     * Check if the authenticator token exists
     * @param authToken
     * @return
     * @throws Database.DatabaseException
     */
    public boolean doesAuthTokenExist(String authToken) throws Database.DatabaseException {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from authTokens WHERE authToken = '" + authToken + "'";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                if (!rs.next() ) {
                    throw new Database.DatabaseException("no such authToken");
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
            throw new Database.DatabaseException("no such authToken");
        }
    }

    /**
     * Return a string object of the entire table
     * @return
     * @throws Database.DatabaseException
     */
    public String tableToString() throws Database.DatabaseException{
        StringBuilder out = new StringBuilder();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from authTokens";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String word = rs.getString(1);
                    String password = rs.getString(2);
                    String email = rs.getString(3);
                    out.append((word + "\t" + password + "\t" + email + "\n"));
                }
            }
            finally {
                if (rs != null) { rs.close(); }
                if (stmt != null) { stmt.close(); }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("seeTable auth failed");
        }
        return out.toString();
    }
}
