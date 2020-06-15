package Services;

import java.sql.Connection;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Result.ClearResult;

public class Clear {
    private Database db;

    public Clear() {
        db = new Database();
    }

    public ClearResult clear() {
        ClearResult cleared = new ClearResult();
        try {
            Connection conn = db.openConnection();
            db.clearTables();
            db.closeConnection(true);
            cleared.setSuccess(true);
            cleared.setMessage("Clear succeeded.");
        } catch (DataAccessException e) {
            e.printStackTrace();
            cleared.setSuccess(false);
            cleared.setMessage("Error " + e.getMessage());
            try {
                db.closeConnection(false);
            } catch (DataAccessException dataAccessException) {
                cleared.setMessage(dataAccessException.getMessage());
                return cleared;
            }
        }

        return cleared;
    }
}
