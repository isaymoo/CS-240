package Services.Tests;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Result.EventIDResult;
import Services.EventID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventIDTest {
    private EventID eventIDService;
    private Database db;
    @BeforeEach
    public void Setup() throws DataAccessException {
        eventIDService = new EventID();
        db = new Database();
        Connection conn = db.openConnection();
        db.clearTables();
    }

    @AfterEach
    public void End() {
        eventIDService = null;
    }
    @Test
    public void EventIDTest1(){
        User testUser = new User("person1", "user1", "password1", "email1@example.com", "firstName1", "lastName1", "m");
        Person testPerson = new Person("person1", "user1", "firstName1", "lastName1", "m", "father1", "mother1", "person2");
        Event testEvent = new Event("marr1", "user1", "person1", 10, (float)39.94, "USA", "Provo", "marriage", 2010);
        AuthToken authToken = new AuthToken("user1", "authToken1");

        EventDao myEventDao = db.getMyEventDao();
        UserDao myUserDao = db.getMyUserDao();
        AuthTokenDao myAuthTokenDao = db.getMyAuthTokenDao();
        PersonDao myPersonDao = db.getMyPersonDao();

        try {
            myPersonDao.insert(testPerson);
            myAuthTokenDao.insert(authToken);
            myUserDao.insert(testUser);
            myEventDao.insert(testEvent);
            db.closeConnection(true);
            EventIDResult result = eventIDService.eventID(testEvent.getEventID(), authToken.getAuthToken());
            assertEquals(true, result.getSuccess());
            System.out.println(result.getAssociatedUserName());
            System.out.println(result.getEventID());
            System.out.println(result.getPersonID());
            System.out.println(result.getLatitude());
            System.out.println(result.getLongitude());
            System.out.println(result.getCountry());
            System.out.println(result.getCity());
            System.out.println(result.getEventType());
            System.out.println(result.getYear());

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void EventIDTest2(){
        User testUser1 = new User("person1", "user1", "password1", "email1@example.com", "firstName1", "lastName1", "m");
        User testUser2 = new User("person2", "user2", "password2", "email2@example.com", "firstName2", "lastName2", "f");
        Person testPerson1 = new Person("person1", "user1", "firstName1", "lastName1", "m", "father1", "mother1", "person2");
        Person testPerson2 = new Person("person2", "user2", "firstName2", "lastName2", "f", "father2", "mother2", "person1");
        Event testEvent1 = new Event("marr1", "user1", "person1", 10, (float)39.94, "USA", "Provo", "marriage", 2010);
        Event testEvent2 = new Event("marr2", "user2", "person2", 10, (float)39.94, "USA", "Provo", "marriage", 2010);
        AuthToken authToken1 = new AuthToken("user1", "authToken1");
        AuthToken authToken2 = new AuthToken("user2", "authToken2");

        EventDao myEventDao = db.getMyEventDao();
        UserDao myUserDao = db.getMyUserDao();
        AuthTokenDao myAuthTokenDao = db.getMyAuthTokenDao();
        PersonDao myPersonDao = db.getMyPersonDao();

        try {
            myPersonDao.insert(testPerson1);
            myAuthTokenDao.insert(authToken1);
            myUserDao.insert(testUser1);
            myEventDao.insert(testEvent1);
            myPersonDao.insert(testPerson2);
            myAuthTokenDao.insert(authToken2);
            myUserDao.insert(testUser2);
            myEventDao.insert(testEvent2);
            db.closeConnection(true);
            EventIDResult result = eventIDService.eventID(testEvent1.getEventID(), authToken2.getAuthToken());
            assertEquals(false, result.getSuccess());
            System.out.println(result.getMessage());

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void EventIDTest3(){
        User testUser = new User("person1", "user1", "password1", "email1@example.com", "firstName1", "lastName1", "m");
        Person testPerson = new Person("person1", "user1", "firstName1", "lastName1", "m", "father1", "mother1", "person2");
        Event testEvent = new Event("marr1", "user1", "person1", 10, (float)39.94, "USA", "Provo", "marriage", 2010);
        AuthToken authToken = new AuthToken("user1", "authToken1");

        EventDao myEventDao = db.getMyEventDao();
        UserDao myUserDao = db.getMyUserDao();
        AuthTokenDao myAuthTokenDao = db.getMyAuthTokenDao();
        PersonDao myPersonDao = db.getMyPersonDao();

        try {
            myPersonDao.insert(testPerson);
            myAuthTokenDao.insert(authToken);
            myUserDao.insert(testUser);
            myEventDao.insert(testEvent);
            db.closeConnection(true);
            EventIDResult result = eventIDService.eventID(testEvent.getEventID(), "AuthToken!");
            assertEquals(false, result.getSuccess());
            System.out.println(result.getMessage());

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


}
