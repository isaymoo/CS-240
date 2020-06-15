package Services;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.AuthToken;
import Model.Event;
import Result.EventIDResult;

public class EventID {
    private Database db;

    public EventID() {
        db = new Database();
    }

    public EventIDResult eventID(String eventID, String authToken) {
        EventIDResult result = new EventIDResult();
        try {
            db.openConnection();
            EventDao myEventDao = db.getMyEventDao();
            AuthTokenDao myAuthTokenDao = db.getMyAuthTokenDao();
            AuthToken token = myAuthTokenDao.find(authToken);
            Event event = myEventDao.find(eventID);
            if (event == null) throw new DataAccessException("error invalid eventID parameter");
            if (token == null) throw new DataAccessException("error invalid auth token");

            if (event.getAssociatedUsername().equals(token.getAssociatedUserID())) {
                result.setAssociatedUserName(event.getAssociatedUsername());
                result.setAssociatedUserName(event.getAssociatedUsername());
                result.setEventID(event.getEventID());
                result.setPersonID(event.getPersonID());
                result.setLatitude(event.getLatitude());
                result.setLongitude(event.getLongitude());
                result.setCountry(event.getCountry());
                result.setCity(event.getCity());
                result.setEventType(event.getEventType());
                result.setYear(event.getYear());
                result.setSuccess(true);
            } else
                throw new DataAccessException("error requested event does not belong to this user");
            db.closeConnection(true);

        } catch (DataAccessException e) {
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            try {
                db.closeConnection(false);
            } catch (DataAccessException dataAccessException) {
                result.setMessage(dataAccessException.getMessage());
            }
        }

        return result;
    }
}
