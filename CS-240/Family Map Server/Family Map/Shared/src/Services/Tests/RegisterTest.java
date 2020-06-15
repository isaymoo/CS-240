package Services.Tests;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.RegisterRequest;
import Result.RegisterResult;
import Services.Register;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

public class RegisterTest {
    private Register registerService;
    private Database db;

    @BeforeEach
    public void Setup() throws DataAccessException {
        registerService = new Register();
        db = new Database();
        Connection conn = db.openConnection();
        db.clearTables();
        db.closeConnection(true);
        db = null;
    }

    @AfterEach
    public void End() {
        registerService = null;
    }
    @Test
    public void RegisterTest1(){
        RegisterRequest request = new RegisterRequest();
        request.setUserName("me");
        request.setPassword("me");
        request.setEmail("me@example.com");
        request.setFirstName("Me");
        request.setLastName("Me");
        request.setGender("m");

        RegisterResult result = registerService.register(request);

        assertEquals(result.getSuccess(), true);
        System.out.println("authToken: " + result.getAuthToken());
        System.out.println("userName: " + result.getUserName());
        System.out.println("personID: " + result.getPersonID());
    }
    @Test
    public void RegisterTest2(){
        RegisterRequest request1 = new RegisterRequest();
        request1.setUserName("me");
        request1.setPassword("me");
        request1.setEmail("me@example.com");
        request1.setFirstName("Me");
        request1.setLastName("Me");
        request1.setGender("m");

        RegisterResult result1 = registerService.register(request1);

        assertEquals(result1.getSuccess(), true);

        RegisterRequest request2 = new RegisterRequest();
        request2.setUserName("me");
        request2.setPassword("notme");
        request2.setEmail("notme@example.com");
        request2.setFirstName("Not");
        request2.setLastName("Me");
        request2.setGender("f");

        RegisterResult result2 = registerService.register(request2);
        assertEquals(result2.getSuccess(), false);
        System.out.println(result2.getMessage());
    }
    @Test
    public void RegisterTest3(){
        RegisterRequest request1 = new RegisterRequest();
        request1.setUserName("me");
        request1.setPassword("me");
        request1.setEmail("me@example.com");
        request1.setFirstName("Me");
        request1.setLastName("Me");
        request1.setGender("mf");

        RegisterResult result1 = registerService.register(request1);

        assertEquals(result1.getSuccess(), false);
        System.out.println(result1.getMessage());
    }
}
