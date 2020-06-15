package DataAccess;

import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDaoTest {
    private Database db;
    private Person newPerson;
    private Person compareTest;
    private Connection conn;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        newPerson = new Person("ABC-P35", "myUserName", "Joe", "Student", "M", "AFFW", "GHQO", "cCCE");
        conn = db.openConnection();
        db.clearTables();
        pDao = new PersonDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(true);
    }

    @Test
    public void insert1() throws DataAccessException {
        pDao.insert(newPerson);
        compareTest = pDao.find(newPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(compareTest, newPerson);
    }

    @Test
    public void insert2() throws DataAccessException {
        pDao.insert(newPerson);
        assertThrows(DataAccessException.class, ()-> pDao.insert(newPerson));
    }
    @Test
    public void insert3() throws DataAccessException {
        pDao.insert(newPerson);
        Person newPerson2 = pDao.find(newPerson.getPersonID());
        newPerson2.setFirstName("Bill");
        assertThrows(DataAccessException.class, ()-> pDao.insert(newPerson2));
    }
    @Test
    public void remove1() throws DataAccessException{
        pDao.insert(newPerson);
        compareTest = pDao.find(newPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(compareTest, newPerson);//why are they not equal?
        pDao.remove(newPerson.getPersonID());
        assertEquals(pDao.getNumEntries(), 0);
    }
    @Test
    public void remove2() throws DataAccessException{
        pDao.insert(newPerson);
        Person person2 = new Person("AAA31", "notMe", "Jimmy", "Stewart", "M", "", "", "");
        pDao.insert(person2);
        compareTest = pDao.find(newPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(compareTest, newPerson);//why are they not equal?
        compareTest = pDao.find(person2.getPersonID());
        assertEquals(compareTest, person2);
        pDao.remove(newPerson.getPersonID());
        assertEquals(pDao.getNumEntries(), 1);
        assertEquals(pDao.find(person2.getPersonID()), person2);
        pDao.remove(person2.getPersonID());
        assertEquals(pDao.getNumEntries(), 0);
    }

}
