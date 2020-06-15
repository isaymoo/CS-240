package Services.Tests;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;
import Services.Load;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

public class LoadTest {
    private Load loadService;
    private Database db;

    @BeforeEach
    public void Setup() throws DataAccessException {
        loadService = new Load();
        db = new Database();
        Connection conn = db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @AfterEach
    public void End() {
        loadService = null;
    }
    @Test
    public void LoadTest1(){
        User testUser1 = new User("person1", "user1", "password1", "email1@example.com", "firstName1", "lastName1", "m");
        User testUser2 = new User("person2", "user2", "password2", "email2@example.com", "firstName2", "lastName2", "f");
        Person testPerson1 = new Person("person1", "user1", "firstName1", "lastName1", "m", "father1", "mother1", "person2");
        Person testPerson2 = new Person("person2", "user2", "firstName2", "lastName2", "f", "father2", "mother2", "person1");
        Event testEvent1 = new Event("marr1", "user1", "person1", 10, (float)39.94, "USA", "Provo", "marriage", 2010);
        Event testEvent2 = new Event("marr2", "user2", "person2", 10, (float)39.94, "USA", "Provo", "marriage", 2010);

        User[] userArray = new User[2];
        userArray[0] = testUser1;
        userArray[1] = testUser2;
        Person[] personArray = new Person[2];
        personArray[0] = testPerson1;
        personArray[1] = testPerson2;
        Event[] eventArray = new Event[2];
        eventArray[0] = testEvent1;
        eventArray[1] = testEvent2;

        LoadRequest request = new LoadRequest();
        request.setUsers(userArray);
        request.setPersons(personArray);
        request.setEvents(eventArray);

        LoadResult result = loadService.load(request);
        assertEquals(true, result.getSuccess());
    }
    @Test
    public void LoadTest2(){
        User testUser1 = new User("", "user1", "password1", "email1@example.com", "firstName1", "lastName1", "m");
        User testUser2 = new User("person2", "user2", "password2", "email2@example.com", "firstName2", "lastName2", "f");
        Person testPerson1 = new Person("person1", "user1", "firstName1", "lastName1", "m", "father1", "mother1", "person2");
        Person testPerson2 = new Person("person2", "user2", "firstName2", "lastName2", "f", "father2", "mother2", "person1");
        Event testEvent1 = new Event("marr1", "user1", "person1", 10, (float)39.94, "USA", "Provo", "marriage", 2010);
        Event testEvent2 = new Event("marr2", "user2", "person2", 10, (float)39.94, "USA", "Provo", "marriage", 2010);

        User[] userArray = new User[2];
        userArray[0] = testUser1;
        userArray[1] = testUser2;
        Person[] personArray = new Person[2];
        personArray[0] = testPerson1;
        personArray[1] = testPerson2;
        Event[] eventArray = new Event[2];
        eventArray[0] = testEvent1;
        eventArray[1] = testEvent2;

        LoadRequest request = new LoadRequest();
        request.setUsers(userArray);
        request.setPersons(personArray);
        request.setEvents(eventArray);

        LoadResult result = loadService.load(request);
        assertEquals(false, result.getSuccess());
    }
}


