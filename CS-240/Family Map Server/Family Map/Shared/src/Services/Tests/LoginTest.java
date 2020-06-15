package Services.Tests;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;
import Services.Login;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {
    private Login loginService;
    private Database db;

    @BeforeEach
    public void setup() throws DataAccessException {
        loginService = new Login();
        db = new Database();
        Connection conn = db.openConnection();
        db.clearTables();
    }
    @AfterEach
    public void end(){
        loginService = null;
    }
    @Test
    public void loginTest1(){
        LoginRequest request = new LoginRequest();
        request.setUserName("me");
        request.setPassword("me");

        UserDao myUserDao = db.getMyUserDao();
        User user = new User("me", "me", "me", "me@example.com", "Me", "Me", "m");
        try {
            myUserDao.insert(user);
            db.closeConnection(true);
            LoginResult result = loginService.login(request);
            assertEquals(result.getSuccess(), true);
            System.out.println("authToken: " + result.getAuthToken());
            System.out.println("userName: " + result.getUserName());
            System.out.println("personID: " + result.getPersonID());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void loginTest2(){
        LoginRequest request = new LoginRequest();
        request.setUserName("me");
        request.setPassword("me");

        UserDao myUserDao = db.getMyUserDao();
        User user = new User("me", "me", "notme", "me@example.com", "Me", "Me", "m");
        try {
            myUserDao.insert(user);
            db.closeConnection(true);
            LoginResult result = loginService.login(request);
            assertEquals(result.getSuccess(), false);
            System.out.println(result.getMessage());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

    }

}
