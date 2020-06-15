package DataAccess;

import Result.ClearResult;
import Model.Event;

import java.sql.*;
import java.util.ArrayList;

public class EventDao {
    private final Connection conn;

    public EventDao(Connection conn)
    {
        this.conn = conn;
    }
    public void insert(Event someEvent) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO event (eventID, associatedUsername, personID, latitude, longitude, " +
                "country, city, eventType, yr) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, someEvent.getEventID());
            stmt.setString(2, someEvent.getAssociatedUsername());
            stmt.setString(3, someEvent.getPersonID());
            stmt.setFloat(4, someEvent.getLatitude());
            stmt.setFloat(5, someEvent.getLongitude());
            stmt.setString(6, someEvent.getCountry());
            stmt.setString(7, someEvent.getCity());
            stmt.setString(8, someEvent.getEventType());
            stmt.setInt(9, someEvent.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }
    public Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM event WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Yr"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
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
    public void remove(String eventID) throws DataAccessException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("DELETE FROM event WHERE eventID = '" + eventID + "'");

            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("delete Event failed");
        }
    }
    public ClearResult clearAll() throws DataAccessException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM event");
            }

            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("reset table failed");
        }
        return null;
    }
    public void removeAllOfUser(String userName){
        Statement stmt = null;
        try {
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM event WHERE associatedUserName = '" + userName + "'");
            }
            finally{
                if (stmt != null) stmt.close();
                stmt = null;
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    public Event[] returnAllOfUser(String userName){
        ArrayList<Event> events = new ArrayList<Event>();
        int numEvents = 0;

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM event WHERE associatedUserName = '" + userName + "'";
            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();
            while(rs.next()){
                Event event = new Event(rs.getString(1),rs.getString(2),rs.getString(3),rs.getFloat(4),
                        rs.getFloat(5),rs.getString(6),rs.getString(7),rs.getString(8),
                        rs.getInt(9));
                events.add(event);
                numEvents++;

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally  {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        Event[] returnValue = new Event[events.size()];
        returnValue = events.toArray(returnValue);

        return returnValue;
    }

}
