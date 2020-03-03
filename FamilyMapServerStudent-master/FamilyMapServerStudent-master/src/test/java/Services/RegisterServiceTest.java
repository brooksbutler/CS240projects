package Services;

import DataAccessObjects.*;
import Model.UserModel;
import Request.RegisterRequest;
import Result.RegisterResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {
    private RegisterService myRegisterService;

    UserModel testUser1 = new UserModel("username1", "password1",
            "email1@email.com","John", "Doe", "m",
            UUID.randomUUID().toString());

    @BeforeEach
    public void setUp() {
        myRegisterService = new RegisterService();
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
    public void tearDown() {
        myRegisterService = null;
    }

    @Test
    public void testRegister1(){
        RegisterRequest inputRequest = new RegisterRequest();
        inputRequest.setGender(testUser1.getGender());
        inputRequest.setUserName(testUser1.getUserName());
        inputRequest.setFirstName(testUser1.getFirstName());
        inputRequest.setLastName(testUser1.getLastName());
        inputRequest.setEmail(testUser1.getEmail());
        inputRequest.setPassword(testUser1.getPassword());

        RegisterResult expectedResult = new RegisterResult();
        expectedResult.setUserName(testUser1.getUserName());
        expectedResult.setPersonId(testUser1.getPersonID());
        expectedResult.setSuccess(true);

        Database db = new Database();
        try {
            db.openConnection();
            db.deleteEverythingOfUser(testUser1);
            db.closeConnection(true);
        } catch (Database.DatabaseException e){
            assertEquals("throwing excpetions is bad", e.getMessage());
        }
        RegisterResult outputResult = myRegisterService.register(inputRequest);
        expectedResult.setAuthToken(outputResult.getAuthToken());
        expectedResult.setPersonId(outputResult.getPersonID());

        assertEquals(expectedResult, outputResult);

        RegisterResult badExpectedResult = new RegisterResult();
        badExpectedResult.setSuccess(false);
        badExpectedResult.setMessage("User already exists failed");

        RegisterResult badOutputResult = myRegisterService.register(inputRequest);
        assertEquals(badExpectedResult, badOutputResult);
    }
}