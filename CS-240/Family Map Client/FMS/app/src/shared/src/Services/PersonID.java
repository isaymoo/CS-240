package Services;


import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.AuthToken;
import Model.Person;
import Result.PersonIDResult;

public class PersonID {
    private Database db;

    public PersonID() {
        db = new Database();
    }

    public PersonIDResult personID(String personID, String authToken) {
        PersonIDResult result = new PersonIDResult();
        try {
            db.openConnection();
            PersonDao myPersonDao = db.getMyPersonDao();
            AuthTokenDao myAuthTokenDao = db.getMyAuthTokenDao();
            Person person = myPersonDao.find(personID);
            AuthToken token = myAuthTokenDao.find(authToken);
            String personUserName = person.getAssociatedUsername();
            if (token == null) throw new DataAccessException("error invalid auth token");
            String tokenUserName = token.getAssociatedUserID();
            if (personUserName.equals(tokenUserName)) {
                result.setAssociatedUserName(person.getAssociatedUsername());
                if (person.getFatherID() != null && !person.getFatherID().equals(""))
                    result.setFatherID(person.getFatherID());
                result.setFirstName(person.getFirstName());
                result.setGender(person.getGender());
                result.setLastName(person.getLastName());
                if (person.getMotherID() != null && !person.getMotherID().equals(""))
                    result.setMotherID(person.getMotherID());
                result.setPersonID(person.getPersonID());
                if (person.getSpouseID() != null && !person.getSpouseID().equals(""))
                    result.setSpouseID(person.getSpouseID());
                result.setSuccess(true);
            } else
                throw new DataAccessException("error requested person does not belong to this user");
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
