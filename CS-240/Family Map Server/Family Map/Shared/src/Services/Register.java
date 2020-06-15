package Services;

import DataAccess.Database;
import DataAccess.*;
import Model.*;
import Request.RegisterRequest;
import Result.RegisterResult;

import javax.xml.crypto.Data;
import java.sql.Connection;

public class Register {
    private Database db;
    public Register(){
        db = new Database();
    }
    public RegisterResult register(RegisterRequest request){
        RegisterResult result = new RegisterResult();
        try {
            Connection conn = db.openConnection();
            UserDao myUserDao = db.getMyUserDao();
            PersonDao myPersonDao = db.getMyPersonDao();
            EventDao myEventDao = db.getMyEventDao();
            AuthTokenDao myAuthDao = db.getMyAuthTokenDao();

            User u = new User(request);
            if (myUserDao.find(u.getUserName()) != null) throw new DataAccessException("error username already taken by another user");

            boolean tryAgain = false;
            Person p = new Person(u);
            u.setPersonID(p.getPersonID());
            myUserDao.insert(u);

            Event birth = new Event(p);
            do {
                try {
                    birth = new Event(p);
                    myEventDao.insert(birth);
                } catch (DataAccessException e) {
                    tryAgain = true;
                }
            } while(tryAgain == true);

            myPersonDao.addGenerations(p, 4, myEventDao, birth.getYear());
            myPersonDao.insert(p);

            AuthToken auth = new AuthToken(u);
            do {
                try {
                    auth = new AuthToken(u);
                    myAuthDao.insert(auth);
                } catch (DataAccessException e) {
                    tryAgain = true;
                }
            } while(tryAgain == true);
            result.setSuccess(true);
            result.setAuthToken(auth.getAuthToken());
            result.setPersonID(p.getPersonID());
            result.setUserName(u.getUserName());
            db.closeConnection(true);

        } catch (DataAccessException e) {
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            try {
                db.closeConnection(false);
            } catch (DataAccessException dataAccessException) {
                result.setSuccess(false);
                result.setMessage(dataAccessException.getMessage());
            }
        }

        return result;
    }

}
