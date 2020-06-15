package Services;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;

public class Login {
    private Database db;
    public Login(){
        db = new Database();
    }

    public LoginResult login(LoginRequest request){

        LoginResult result = new LoginResult();
        try {
            db.openConnection();
            UserDao myUserDao = db.getMyUserDao();
            AuthTokenDao myAuthDao = db.getMyAuthTokenDao();

            User user = myUserDao.find(request.getUserName());
            if (user == null) throw new DataAccessException("Error User Not Found");
            String userPassword = user.getPassword();
            String requestPassword = request.getPassword();
            if (userPassword.equals(requestPassword)){
                AuthToken token = new AuthToken(user);
                myAuthDao.insert(token);
                result.setAuthToken(token.getAuthToken());
                result.setUserName(token.getAssociatedUserID());
                result.setPersonID(user.getPersonID());
                result.setSuccess(true);
                db.closeConnection(true);
            }
            else throw new DataAccessException("Error Password Incorrect");
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
