package Services;

import DataAccessObjects.*;
import Model.AuthTokenModel;
import Model.PersonModel;
import Model.UserModel;
import Result.PersonGetAllResult;
import Result.PersonIDResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class PersonGetAllServiceTest {
    private PersonGetAllService myPersonGetAllService;

    UserModel testUser1 = new UserModel("username1", "password1",
            "email1@email.com","John", "Doe", "m",
            UUID.randomUUID().toString());
    UserModel testUser2 = new UserModel("username2", "password2",
            "email2@email.com","Jane", "Doe", "f",
            UUID.randomUUID().toString());

    PersonModel testPerson1 = new PersonModel(testUser1);
    PersonModel testPerson2 = new PersonModel(testUser2);
    PersonModel testPerson3 = new PersonModel(UUID.randomUUID().toString(), "username1",
            "Jack", "Doe", "m");

    AuthTokenModel testAuth1 = new AuthTokenModel(UUID.randomUUID().toString(),
            testUser1.getPersonID(), testUser1.getUserName());

    AuthTokenModel testAuth2 = new AuthTokenModel(UUID.randomUUID().toString(),
            testUser2.getPersonID(), testUser2.getUserName());

    @BeforeEach
    public void setUp() {
        myPersonGetAllService = new PersonGetAllService();
        testPerson1.setSpouseID("Druzinha"); testPerson1.setMotherID("Nelly"); testPerson1.setFatherID("Jeffrey");
        testPerson2.setSpouseID("Daniel"); testPerson2.setFatherID("Jeffrey"); testPerson2.setMotherID("Nelly");
        testPerson3.setSpouseID(UUID.randomUUID().toString());testPerson3.setFatherID(UUID.randomUUID().toString());
        testPerson3.setMotherID(UUID.randomUUID().toString());

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
            myPersonDAO.insertPerson(testPerson3);
            myAuthDAO.insertToken(testAuth1); myAuthDAO.insertToken(testAuth2);
            db.closeConnection(true);
        } catch (Database.DatabaseException e){
            assertEquals("throwing excpetions is bad", e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        myPersonGetAllService = null;
    }

    @Test
    public void testPersonGetAll1(){
        PersonIDResult[] inputPersons = new PersonIDResult[2];
        inputPersons[0] = new PersonIDResult(testPerson1);
        inputPersons[1] = new PersonIDResult(testPerson3);

        PersonGetAllResult expectedResult = new PersonGetAllResult();
        expectedResult.setData(inputPersons);
        expectedResult.setSuccess(true);

        PersonGetAllResult outputResponse = myPersonGetAllService.personGetAll(testAuth1.getAuthToken());

        assertEquals(expectedResult, outputResponse);
    }

    @Test
    public void testPersonGetAll2(){
        PersonGetAllResult badExpectedResponse = new PersonGetAllResult();
        badExpectedResponse.setSuccess(false);
        badExpectedResponse.setMessage("no such authToken");

        PersonGetAllResult badOutputResponse = myPersonGetAllService.personGetAll("bogus");

        assertEquals(badExpectedResponse, badOutputResponse);
    }
}