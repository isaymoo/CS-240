package DataAccess;

import Model.AuthToken;
import Result.ClearResult;

public class AuthTokenDao {
    private final Connection conn;
    private int numEntries = 0;

    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    public ClearResult clearAll() throws DataAccessException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("DELETE FROM authToken");
            } finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("reset table failed");
        }
        return null;
    }

    public void insert(AuthToken token) throws DataAccessException {
        String sql = "INSERT INTO authToken (associatedUserName, authToken) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, token.getAssociatedUserID());
            stmt.setString(2, token.getAuthToken());

            stmt.executeUpdate();
            numEntries++;
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    public AuthToken find(String tokenID) throws DataAccessException {
        AuthToken token;
        ResultSet rs = null;
        String sql = "SELECT * FROM authToken WHERE authToken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tokenID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                token = new AuthToken(rs.getString("associatedUserName"), rs.getString("authToken"));
                return token;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding token");
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

}
