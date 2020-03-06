package Services;

import DataAccessObjects.*;
import Model.AuthTokenModel;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import Result.EventIDResult;
import Result.PersonIDResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class PersonIDServiceTest {
    private PersonIDService myPersonIDService;

    UserModel testUser1 = new UserModel("username1", "password1",
            "email1@email.com","John", "Doe", "m",
            UUID.randomUUID().toString());
    UserModel testUser2 = new UserModel("username2", "password2",
            "email2@email.com","Jane", "Doe", "f",
            UUID.randomUUID().toString());

    PersonModel testPerson1 = new PersonModel(testUser1);
    PersonModel testPerson2 = new PersonModel(testUser2);

    AuthTokenModel testAuth1 = new AuthTokenModel(UUID.randomUUID().toString(),
            testUser1.getPersonID(), testUser1.getUserName());

    AuthTokenModel testAuth2 = new AuthTokenModel(UUID.randomUUID().toString(),
            testUser2.getPersonID(), testUser2.getUserName());

    @BeforeEach
    public void setUp() {
        myPersonIDService = new PersonIDService();
        testPerson1.setSpouseID("Druzinha");
        testPerson1.setMotherID("Nelly");
        testPerson1.setFatherID("Jeffrey");

        testPerson2.setSpouseID("Daniel");
        testPerson2.setFatherID("Jeffrey");
        testPerson2.setMotherID("Nelly");
        Database db = new Database();
        try {
            db.openConnection();
            db.resetTables();
            db.closeConnection(true);

            db.openConnection();
            UserDAO myUserDAO = db.getMyUserDAO();
            PersonDAO myPersonDAO = db.getMyPersonDAO();
            EventDAO myEventDAO = db.getMyEventDAO();
            AuthTokenDAO myAuthDAO = db.getMyAuthTokenDAO();

            myUserDAO.insertUser(testUser1); myUserDAO.insertUser(testUser2);
            myPersonDAO.insertPerson(testPerson1); myPersonDAO.insertPerson(testPerson2);
            myAuthDAO.insertToken(testAuth1); myAuthDAO.insertToken(testAuth2);
            db.closeConnection(true);
        } catch (Database.DatabaseException e){
            assertEquals("throwing excpetions is bad", e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() throws Database.DatabaseException {
        myPersonIDService = null;
        Database db = new Database();
        db.openConnection();
        db.resetTables();
        db.closeConnection(true);
    }

    @Test
    public void testPersonID1(){
        PersonIDResult expectedResult = new PersonIDResult(testPerson1);
        expectedResult.setSuccess(true);

        PersonIDResult outputResult = myPersonIDService.personID(testPerson1.getPersonID(), testAuth1.getAuthToken());

        assertEquals(expectedResult, outputResult);
    }

    @Test
    public void testPersonID2(){
        PersonIDResult badExpectedResult = new PersonIDResult();
        badExpectedResult.setSuccess(false);
        badExpectedResult.setMessage("error: PersonID does not match given authToken");

        PersonIDResult badOutputResult = myPersonIDService.personID(testPerson1.getPersonID(), testAuth2.getAuthToken());

        assertEquals(badExpectedResult, badOutputResult);
    }
}