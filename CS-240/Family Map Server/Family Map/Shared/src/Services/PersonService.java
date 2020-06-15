package Services;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.AuthToken;
import Model.Person;
import Result.PersonResult;

public class PersonService {
    private Database db;
    public PersonService(){
        db = new Database();
    }
    public PersonResult person(String authToken){
        PersonResult result = new PersonResult();

        try {
            db.openConnection();
            PersonDao myPersonDao = db.getMyPersonDao();
            AuthTokenDao myAuthTokenDao = db.getMyAuthTokenDao();
            AuthToken token = myAuthTokenDao.find(authToken);
            if(token == null) throw new DataAccessException("error invalid auth token");
            Person[] allPersons = myPersonDao.returnAllOfUser(token.getAssociatedUserID());
            result.setPersons(allPersons);
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
