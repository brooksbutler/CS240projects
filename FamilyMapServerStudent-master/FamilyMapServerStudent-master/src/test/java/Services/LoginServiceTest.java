package Services;

import DataAccessObjects.*;
import Model.UserModel;
import Request.LoginRequest;
import Result.LoginResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    private LoginService myLoginService;

    UserModel testUser1 = new UserModel("username1", "password1",
            "email1@email.com","John", "Doe", "m",
            UUID.randomUUID().toString());

    @BeforeEach
    public void setUp() {
        myLoginService = new LoginService();
        Database db = new Database();
        try {
            db.openConnection();
            db.resetTables();
            db.closeConnection(true);

            db.openConnection();
            UserDAO myUserDAO = db.getMyUserDAO();
            myUserDAO.insertUser(testUser1);
            db.closeConnection(true);
        } catch (Database.DatabaseException e){
            assertEquals("throwing excpetions is bad", e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() throws Database.DatabaseException {
        myLoginService = null;
        Database db = new Database();
        db.openConnection();
        db.resetTables();
        db.closeConnection(true);
    }

    @Test
    public void testLogin1(){
        LoginRequest inputRequest = new LoginRequest();
        inputRequest.setUserName(testUser1.getUserName());
        inputRequest.setPassword(testUser1.getPassword());

        LoginResult expectedResult = new LoginResult();
        expectedResult.setSuccess(true);
        expectedResult.setUserName(testUser1.getUserName());
        expectedResult.setPersonId(testUser1.getPersonID());

        LoginResult outputResult = myLoginService.login(inputRequest);
        expectedResult.setAuthToken(outputResult.getAuthToken());

        assertEquals(expectedResult, outputResult);
    }

    @Test
    public void testLogin2(){
        LoginRequest inputRequest = new LoginRequest();
        LoginResult badExpectedResult = new LoginResult();
        badExpectedResult.setSuccess(false);
        badExpectedResult.setMessage("error: no such username and/or password");

        inputRequest.setUserName("Bogus");
        LoginResult badOutputResponse = myLoginService.login(inputRequest);

        assertEquals(badExpectedResult, badOutputResponse);
    }
}