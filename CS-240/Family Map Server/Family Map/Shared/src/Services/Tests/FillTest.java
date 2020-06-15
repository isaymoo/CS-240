package Services.Tests;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.User;
import Result.FillResult;
import Services.Fill;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

public class FillTest {
    private Fill fillService;
    private Database db;
    @BeforeEach
    public void Setup() throws DataAccessException {
        fillService = new Fill();
        db = new Database();
        Connection conn = db.openConnection();
        db.clearTables();
    }
    @AfterEach
    public void End() {
        fillService = null;
    }

    @Test
    public void FillTest1(){
        UserDao myUserDao = db.getMyUserDao();
        User testUser = new User("person1", "user1", "password1", "email1@example.com", "firstName1", "lastName1", "m");
        try {
            myUserDao.insert(testUser);
            db.closeConnection(true);
            FillResult result = fillService.fill(testUser.getUserName(), 4);
            assertEquals("Successfully added 31 persons and 94 events to the database", result.getMessage());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void FillTest2(){
        UserDao myUserDao = db.getMyUserDao();
        User testUser = new User("person1", "user1", "password1", "email1@example.com", "firstName1", "lastName1", "m");
        try {
            db.closeConnection(true);
            FillResult result = fillService.fill(testUser.getUserName(), 4);
            assertEquals(false, result.getSuccess());
            System.out.println(result.getMessage());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
