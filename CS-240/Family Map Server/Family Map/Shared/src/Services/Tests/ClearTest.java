package Services.Tests;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Result.ClearResult;
import Services.Clear;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ClearTest {
    private Clear clearService;
    private Database db;

    @BeforeEach
    public void Setup() throws DataAccessException {
        clearService = new Clear();
        db = new Database();
        Connection conn = db.openConnection();
        db.clearTables();
    }

    @AfterEach
    public void End() {
        clearService = null;
    }

    @Test
    public void TestClear1(){
        User testUser1 = new User("person1", "user1", "password1", "email1@example.com", "firstName1", "lastName1", "m");
        User testUser2 = new User("person2", "user2", "password2", "email2@example.com", "firstName2", "lastName2", "f");
        Person testPerson1 = new Person("person1", "user1", "firstName1", "lastName1", "m", "father1", "mother1", "person2");
        Person testPerson2 = new Person("person2", "user2", "firstName2", "lastName2", "f", "father2", "mother2", "person1");
        Event testEvent1 = new Event("marr1", "user1", "person1", 10, (float)39.94, "USA", "Provo", "marriage", 2010);
        Event testEvent2 = new Event("marr2", "user2", "person2", 10, (float)39.94, "USA", "Provo", "marriage", 2010);
        AuthToken authToken1 = new AuthToken("user1", "authToken1");
        AuthToken authToken2 = new AuthToken("user2", "authToken2");

        try {

            UserDao myUserDao = db.getMyUserDao();
            PersonDao myPersonDao = db.getMyPersonDao();
            EventDao myEventDao = db.getMyEventDao();
            AuthTokenDao myAuthTokenDao = db.getMyAuthTokenDao();

            myUserDao.insert(testUser1);
            myUserDao.insert(testUser2);
            myPersonDao.insert(testPerson1);
            myPersonDao.insert(testPerson2);
            myEventDao.insert(testEvent1);
            myEventDao.insert(testEvent2);
            myAuthTokenDao.insert(authToken1);
            myAuthTokenDao.insert(authToken2);

            String expectedOutput = "Clear succeeded.";

            db.closeConnection(true);
            ClearResult result = clearService.clear();
            assertEquals(expectedOutput, result.getMessage());


        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void TestClear2(){
        User testUser1 = new User("willBeChanged", "user1", "password1", "email1@example.com", "firstName1", "lastName1", "m");
        User testUser2 = new User("willBeChanged", "user2", "password2", "email2@example.com", "firstName2", "lastName2", "f");
        Person testPerson1 = new Person(testUser1);
        Person testPerson2 = new Person(testUser2);
        testUser1.setPersonID(testPerson1.getPersonID());
        testUser2.setPersonID(testPerson2.getPersonID());
        Event testEvent1 = new Event(testPerson1);
        Event testEvent2 = new Event(testPerson2);
        AuthToken authToken1 = new AuthToken(testUser1);
        AuthToken authToken2 = new AuthToken(testUser2);

        try {

            UserDao myUserDao = db.getMyUserDao();
            PersonDao myPersonDao = db.getMyPersonDao();
            EventDao myEventDao = db.getMyEventDao();
            AuthTokenDao myAuthTokenDao = db.getMyAuthTokenDao();

            myUserDao.insert(testUser1);
            myUserDao.insert(testUser2);
            myPersonDao.insert(testPerson1);
            myPersonDao.insert(testPerson2);
            myEventDao.insert(testEvent1);
            myEventDao.insert(testEvent2);
            myAuthTokenDao.insert(authToken1);
            myAuthTokenDao.insert(authToken2);

            String expectedOutput = "Error clear table failed";


            ClearResult result = clearService.clear();
            db.closeConnection(true);
            assertEquals(expectedOutput, result.getMessage());


        } catch (DataAccessException e) {
            e.printStackTrace();
        }

    }
}
