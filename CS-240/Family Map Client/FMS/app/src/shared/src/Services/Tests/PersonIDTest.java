package Services.Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import Model.AuthToken;
import Model.Person;
import Model.User;
import Result.PersonIDResult;
import Services.PersonID;

import static org.junit.jupiter.api.Assertions.*;

public class PersonIDTest {
    private PersonID personIDService;
    private Database db;

    @BeforeEach
    public void Setup() throws DataAccessException {
        personIDService = new PersonID();
        db = new Database();
        Connection conn = db.openConnection();
        db.clearTables();
    }

    @AfterEach
    public void End() {
        personIDService = null;
    }

    @Test
    public void PersonIDTest1() {
        User testUser = new User("person1", "user1", "password1", "email1@example.com", "firstName1", "lastName1", "m");
        Person testPerson = new Person("person1", "user1", "firstName1", "lastName1", "m", "father1", "mother1", "person2");
        AuthToken authToken = new AuthToken("user1", "authToken1");
        PersonDao myPersonDao = db.getMyPersonDao();
        AuthTokenDao myAuthTokenDao = db.getMyAuthTokenDao();
        UserDao myUserDao = db.getMyUserDao();
        try {
            myPersonDao.insert(testPerson);
            myAuthTokenDao.insert(authToken);
            myUserDao.insert(testUser);
            db.closeConnection(true);
            PersonIDResult result = personIDService.personID("person1", authToken.getAuthToken());
            assertEquals(true, result.getSuccess());
            System.out.println(result.getAssociatedUserName());
            System.out.println(result.getPersonID());
            System.out.println(result.getFirstName());
            System.out.println(result.getLastName());
            System.out.println(result.getGender());
            if (result.getFatherID() != "") System.out.println(result.getFatherID());
            if (result.getMotherID() != "") System.out.println(result.getMotherID());
            if (result.getSpouseID() != "") System.out.println(result.getSpouseID());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void PersonIDTest2() {
        User testUser = new User("person1", "user1", "password1", "email1@example.com", "firstName1", "lastName1", "m");
        Person testPerson = new Person("person1", "user1", "firstName1", "lastName1", "m", "father1", "mother1", "person2");
        AuthToken authToken = new AuthToken("user1", "authToken1");
        PersonDao myPersonDao = db.getMyPersonDao();
        AuthTokenDao myAuthTokenDao = db.getMyAuthTokenDao();
        UserDao myUserDao = db.getMyUserDao();
        try {
            myPersonDao.insert(testPerson);
            myAuthTokenDao.insert(authToken);
            myUserDao.insert(testUser);
            db.closeConnection(true);
            PersonIDResult result = personIDService.personID("person1", "authToken2");
            assertEquals(false, result.getSuccess());
            System.out.println(result.getMessage());

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void PersonIDTest3() {
        User testUser = new User("person1", "user1", "password1", "email1@example.com", "firstName1", "lastName1", "m");
        User testUser2 = new User("person2", "user2", "password2", "email2@example.com", "firstName2", "lastName2", "f");
        Person testPerson1 = new Person("person1", "user1", "firstName1", "lastName1", "m", "father1", "mother1", "person2");
        Person testPerson2 = new Person("person2", "user2", "firstName2", "lastName2", "f", "father2", "mother2", "person1");
        AuthToken authToken = new AuthToken("user2", "authToken2");
        PersonDao myPersonDao = db.getMyPersonDao();
        AuthTokenDao myAuthTokenDao = db.getMyAuthTokenDao();
        UserDao myUserDao = db.getMyUserDao();
        try {
            myPersonDao.insert(testPerson1);
            myPersonDao.insert(testPerson2);
            myAuthTokenDao.insert(authToken);
            myUserDao.insert(testUser);
            myUserDao.insert(testUser2);
            db.closeConnection(true);
            PersonIDResult result = personIDService.personID("person1", "authToken2");
            assertEquals(false, result.getSuccess());
            System.out.println(result.getMessage());

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
