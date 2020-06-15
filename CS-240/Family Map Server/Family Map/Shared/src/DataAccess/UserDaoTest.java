package DataAccess;


import Model.Person;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserDaoTest {

    private Database db;
    private User newUser;
    Connection conn;
    UserDao uDao;
    User compareTest;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        newUser = new User("ABC-P35", "myUserName","abc123", "myUserName@example.com", "Joe", "Student", "M");
        conn = db.openConnection();
        db.clearTables();
        uDao = new UserDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(true);
    }

    @Test
    public void insert1() throws DataAccessException {
        uDao.insert(newUser);
        User compareTest = uDao.find(newUser.getUserName());
        assertNotNull(compareTest);
        assertEquals(newUser, compareTest);
    }

    @Test
    public void insert2() throws DataAccessException {
        uDao.insert(newUser);
        assertThrows(DataAccessException.class, ()-> uDao.insert(newUser));
    }
    @Test
    public void insert3() throws DataAccessException {
        uDao.insert(newUser);
        User newUser2 = uDao.find(newUser.getUserName());
        newUser.setFirstName("Bill");
        assertThrows(DataAccessException.class, ()-> uDao.insert(newUser2));
    }
    @Test
    public void remove1() throws DataAccessException{
        uDao.insert(newUser);
        compareTest = uDao.find(newUser.getUserName());
        assertNotNull(compareTest);
        assertEquals(compareTest, newUser);//why are they not equal?
        uDao.remove(newUser.getUserName());
        assertEquals(uDao.getNumEntries(), 0);
    }
    @Test
    public void remove2() throws DataAccessException{
        uDao.insert(newUser);
        User person2 = new User("AAA31", "notMe", "Jimmy", "jStewart@example.com", "Jimmy", "Stewart", "M");
        uDao.insert(person2);
        compareTest = uDao.find(newUser.getUserName());
        assertNotNull(compareTest);
        assertEquals(compareTest, newUser);//why are they not equal?
        compareTest = uDao.find(person2.getUserName());
        assertEquals(compareTest, person2);
        uDao.remove(newUser.getUserName());
        assertEquals(uDao.getNumEntries(), 1);
        assertEquals(uDao.find(person2.getUserName()), person2);
        uDao.remove(person2.getUserName());
        assertEquals(uDao.getNumEntries(), 0);
    }
}
