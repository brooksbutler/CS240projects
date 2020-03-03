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
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists events");
                stmt.executeUpdate("create table events (eventID VARCHAR(255) NOT NULL UNIQUE , "+
                        "userName VARCHAR(255), personID VARCHAR(255) NOT NULL, latitude REAL NOT NULL, " +
                        "longitude REAL NOT NULL, country CHAR(255) NOT NULL, city VARCHAR(255) NOT NULL,"+
                        "eventType CHAR(255) NOT NULL, year INTEGER NOT NULL,"+
                        " CONSTRAINT event_info UNIQUE (eventID))");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("reset Event Table failed");
        }
    }

    /**
     * Insterts event into database
     * @param event
     * @throws Database.DatabaseException
     */
    public void insertEvent(EventModel event) throws Database.DatabaseException {
        String sql = "insert into events (eventID, userName, personID, latitude, longitude, country, city, eventType, year)"+
                " values (?,?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,event.getEventID());
            stmt.setString(2,event.geUserName());
            stmt.setString(3,event.getPersonID());
            stmt.setDouble(4,event.getLatitude());
            stmt.setDouble(5,event.getLongitude());
            stmt.setString(6,event.getCountry());
            stmt.setString(7,event.getCity());
            stmt.setString(8,event.getEventType());
            stmt.setInt(9,event.getYear());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new Database.DatabaseException("Error encountered while inserting event into the database");
        }

    }

    /**
     * Gets a single event based on an event ID
     * @param eventID
     * @return
     * @throws Database.DatabaseException
     */
    public EventModel selectSingleEvent(String eventID) throws Database.DatabaseException {
        EventModel event = new EventModel();
        ResultSet rs = null;
        String sql = "SELECT * FROM events WHERE eventID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event.setEventID(rs.getString(1));
                event.setUserName(rs.getString(2));
                event.setPersonID(rs.getString(3));
                event.setLatitude(rs.getDouble(4));
                event.setLongitude(rs.getDouble(5));
                event.setCountry(rs.getString(6));
                event.setCity(rs.getString(7));
                event.setEventType(rs.getString(8));
                event.setYear(rs.getInt(9));
                return event;
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
     * Check if an event exists
     * @param eventId
     * @return
     * @throws Database.DatabaseException
     */
    public boolean doesEventExist(String eventId) throws Database.DatabaseException{
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from events WHERE eventID = '" + eventId + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();

                if (!rs.next() ) {
                    throw new Database.DatabaseException("no such eventID");
                } else {
                    return true;
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
            throw new Database.DatabaseException("no such eventID");
        }
    }


    /**
     * Return the event table as a string object
     * @return
     * @throws Database.DatabaseException
     */
    public String tableToString() throws Database.DatabaseException {
        StringBuilder out = new StringBuilder();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from events";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String eventID = rs.getString(1);
                    String userName = rs.getString(2);
                    String personId = rs.getString(3);
                    Double latitude = rs.getDouble(4);
                    Double longitude = rs.getDouble(5);
                    String country = rs.getString(6);
                    String city = rs.getString(7);
                    String eventType = rs.getString(8);
                    int year = rs.getInt(9);

                    out.append((eventID + "\t" + userName + "\t" + personId + "\t" + latitude + "\t" +
                            longitude + "\t" + country + "\t" + city + "\t" + eventType + "\t" + year + "\n"));
                }
            }
            finally {
                if (rs != null) { rs.close(); }
                if (stmt != null) { stmt.close(); }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("Table to string events failed");
        }
        return out.toString();
    }

    public void deleteAllEventsOfUser(UserModel u) throws Database.DatabaseException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM events WHERE userName = '" + u.getUserName() + "'");
            }
            finally {
                if (stmt != null) { stmt.close(); }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("deleteAllEventsOfUser failed");
        }
    }
}
