package Services;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.AuthToken;
import Model.Event;
import Result.EventResult;


public class EventService {
    private Database db;

    public EventService (){
        db = new Database();
    }
    public EventResult event(String authToken){
        EventResult result = new EventResult();

        try {
            db.openConnection();
            EventDao myEventDao = db.getMyEventDao();

            AuthTokenDao myAuthTokenDao = db.getMyAuthTokenDao();
            AuthToken token = myAuthTokenDao.find(authToken);
            if(token == null) throw new DataAccessException("error invalid auth token");
            Event[] array = myEventDao.returnAllOfUser(token.getAssociatedUserID());
            result.setEvents(array);
            db.closeConnection(true);
            result.setSuccess(true);
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
