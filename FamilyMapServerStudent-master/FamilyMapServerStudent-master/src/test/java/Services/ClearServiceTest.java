package Services;

import DataAccessObjects.*;
import Model.AuthTokenModel;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import Result.ClearResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {
    private ClearService myClearService;

    UserModel testUser1 = new UserModel("username1", "password1",
            "email1@email.com","John", "Doe", "m",
            UUID.randomUUID().toString());
    UserModel testUser2 = new UserModel("username2", "password2",
            "email2@email.com","Jane", "Doe", "f",
            UUID.randomUUID().toString());
    EventModel testEvent1 = new EventModel(UUID.randomUUID().toString(),"username1",
            UUID.randomUUID().toString(), -1.0, 1.0, "Country", "City",
            "Some event type", 3000);
    EventModel testEvent2 = new EventModel(UUID.randomUUID().toString(),"username2",
            UUID.randomUUID().toString(), -99.0, 99.0, "Country", "City",
            "Some other event type", 2000);

    PersonModel testPerson1 = new PersonModel(testUser1);
    PersonModel testPerson2 = new PersonModel(testUser2);

    AuthTokenModel testAuth1 = new AuthTokenModel(UUID.randomUUID().toString(),
            testUser1.getPersonID(), testUser1.getUserName());

    AuthTokenModel testAuth2 = new AuthTokenModel(UUID.randomUUID().toString(),
            testUser2.getPersonID(), testUser2.getUserName());

    @BeforeEach
    public void setUp() throws Database.DatabaseException {
        myClearService = new ClearService();
        testPerson1.setSpouseID("Druzinha");
        testPerson1.setMotherID("Nelly");
        testPerson1.setFatherID("Jeffrey");

        testPerson2.setSpouseID("Daniel");
        testPerson2.setFatherID("Jeffrey");
        testPerson2.setMotherID("Nelly");

        Database db = new Database();
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
        myEventDAO.insertEvent(testEvent1); myEventDAO.insertEvent(testEvent2);
        myAuthDAO.insertToken(testAuth1); myAuthDAO.insertToken(testAuth2);
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws Database.DatabaseException {
        myClearService = null;
        Database db = new Database();
        db.openConnection();
        db.resetTables();
        db.closeConnection(true);
    }

    @Test
    public void testClearService1(){
        ClearResult expectedResult = new ClearResult();
        expectedResult.setMessage("Clear succeeded");

        ClearResult output = myClearService.clear();

        assertEquals(expectedResult.getMessage(), output.getMessage());
    }

    @Test
    public void testClearService2(){
        try{
            String expected = "";

            Database db = new Database();
            db.openConnection();
            db.resetTables();
            UserDAO myUserDAO2 = db.getMyUserDAO();
            PersonDAO myPersonDAO2 = db.getMyPersonDAO();
            EventDAO myEventDAO2 = db.getMyEventDAO();
            AuthTokenDAO myAuthDAO2 = db.getMyAuthTokenDAO();

            assertEquals(expected, myUserDAO2.tableToString() + myAuthDAO2.tableToString() +
                    myEventDAO2.tableToString() + myPersonDAO2.tableToString());

            db.closeConnection(true);
        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }


}