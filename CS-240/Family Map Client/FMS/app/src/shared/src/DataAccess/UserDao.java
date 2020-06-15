package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.User;
import Result.ClearResult;

public class UserDao {
    private final Connection conn;
    private int numEntries = 0;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    public void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO user (personID, userName, password, email, firstName, " +
                "lastName, gender) VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (!user.getGender().equals("m")) {
                if (!user.getGender().equals("f"))
                    throw new DataAccessException("error invalid gender");
            }
            if (user.getPersonID() == "" || user.getPassword() == "" || user.getUserName() == "" ||
                    user.getFirstName() == "" || user.getLastName() == "" || user.getEmail() == "")
                throw new DataAccessException("error missing parameter");
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getPersonID());
            stmt.setString(2, user.getUserName());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getFirstName());
            stmt.setString(6, user.getLastName());
            stmt.setString(7, user.getGender());

            stmt.executeUpdate();
            numEntries++;
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    public User find(String userName) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM user WHERE userName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("personID"), rs.getString("userName"),
                        rs.getString("password"), rs.getString("email"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public void remove(String userName) throws DataAccessException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("DELETE FROM user WHERE username = '" + userName + "'");
                numEntries--;
            } finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("delete user failed");
        }
    }

    public ClearResult clearAll() throws DataAccessException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM user");
                numEntries = 0;
            } finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("clear table failed");
        }
        return null;
    }

    public int getNumEntries() {
        return numEntries;
    }
}
