package Services.Tests;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Result.EventResult;
import Result.PersonResult;
import Services.EventService;
import Services.PersonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonServiceTest {
    private PersonService personService;
    private Database db;
    @BeforeEach
    public void Setup() throws DataAccessException {
        personService = new PersonService();
        db = new Database();
        Connection conn = db.openConnection();
        db.clearTables();
    }

    @AfterEach
    public void End() {
        personService = null;
    }
    @Test
    public void PersonTest1(){
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
            PersonResult result = personService.person(authToken.getAuthToken());
            assertEquals(true, result.getSuccess());
            result.printAllPersons();

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void PersonTest2(){
        User testUser = new User("person1", "user1", "password1", "email1@example.com", "firstName1", "lastName1", "m");
        Person testPerson1 = new Person("person1", "user1", "firstName1", "lastName1", "m", "person2", "person3", "");
        Person testPerson2 = new Person("person2", "user1", "firstName2", "lastName2", "m", "father1", "mother1", "person3");
        Person testPerson3 = new Person("person3", "user1", "firstName3", "lastName3", "f", "father1", "mother1", "person2");
        Event testEvent = new Event("marr1", "user1", "person1", 10, (float)39.94, "USA", "Provo", "marriage", 2010);
        AuthToken authToken = new AuthToken("user1", "authToken1");

        EventDao myEventDao = db.getMyEventDao();
        UserDao myUserDao = db.getMyUserDao();
        AuthTokenDao myAuthTokenDao = db.getMyAuthTokenDao();
        PersonDao myPersonDao = db.getMyPersonDao();

        try {
            myPersonDao.insert(testPerson1);
            myPersonDao.insert(testPerson2);
            myPersonDao.insert(testPerson3);
            myAuthTokenDao.insert(authToken);
            myUserDao.insert(testUser);
            myEventDao.insert(testEvent);
            db.closeConnection(true);
            PersonResult result = personService.person(authToken.getAuthToken());
            assertEquals(true, result.getSuccess());
            result.printAllPersons();

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void PersonTest3(){
        User testUser = new User("person1", "user1", "password1", "email1@example.com", "firstName1", "lastName1", "m");
        User testUser2 = new User("person2", "user2", "password2", "email2@example.com", "firstName2", "lastName2", "m");
        Person testPerson1 = new Person("person1", "user1", "firstName1", "lastName1", "m", "person2", "person3", "");
        Person testPerson2 = new Person("person2", "user1", "firstName2", "lastName2", "m", "father1", "mother1", "person3");
        Person testPerson3 = new Person("person3", "user1", "firstName3", "lastName3", "f", "father1", "mother1", "person2");
        Event testEvent = new Event("marr1", "user1", "person1", 10, (float)39.94, "USA", "Provo", "marriage", 2010);
        AuthToken authToken = new AuthToken("user2", "authToken1");

        EventDao myEventDao = db.getMyEventDao();
        UserDao myUserDao = db.getMyUserDao();
        AuthTokenDao myAuthTokenDao = db.getMyAuthTokenDao();
        PersonDao myPersonDao = db.getMyPersonDao();

        try {
            myPersonDao.insert(testPerson1);
            myPersonDao.insert(testPerson2);
            myPersonDao.insert(testPerson3);
            myAuthTokenDao.insert(authToken);
            myUserDao.insert(testUser);
            myUserDao.insert(testUser2);
            myEventDao.insert(testEvent);
            db.closeConnection(true);
            PersonResult result = personService.person("authToken2");
            assertEquals(false, result.getSuccess());
            System.out.println(result.getMessage());

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
