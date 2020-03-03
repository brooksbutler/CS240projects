package Services;

import DataAccessObjects.*;
import Model.AuthTokenModel;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import Request.LoadRequest;
import Result.FillResult;
import Result.LoadResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.Random;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest {
    private LoadService myLoadService;

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
    EventModel testEvent3 = new EventModel(UUID.randomUUID().toString(),"username1",
            UUID.randomUUID().toString(), -999.0, 999.0, "Country", "City",
            "Some third event type", 2100);

    PersonModel testPerson1 = new PersonModel(testUser1);
    PersonModel testPerson2 = new PersonModel(testUser2);

    AuthTokenModel testAuth1 = new AuthTokenModel(UUID.randomUUID().toString(),
            testUser1.getPersonID(), testUser1.getUserName());

    AuthTokenModel testAuth2 = new AuthTokenModel(UUID.randomUUID().toString(),
            testUser2.getPersonID(), testUser2.getUserName());

    @BeforeEach
    public void setUp() {
        myLoadService = new LoadService();
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
            myEventDAO.insertEvent(testEvent1); myEventDAO.insertEvent(testEvent2); myEventDAO.insertEvent(testEvent3);
            myAuthDAO.insertToken(testAuth1); myAuthDAO.insertToken(testAuth2);
            db.closeConnection(true);
        } catch (Database.DatabaseException e){
            assertEquals("throwing excpetions is bad", e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        myLoadService = null;
    }

    @Test
    public void testLoad(){

        UserModel[] usersInput = new UserModel[2];
        usersInput[0] = testUser1;
        usersInput[1] = testUser2;
        PersonModel[] personsInput = new PersonModel[2];
        personsInput[0] = testPerson1;
        personsInput[1] = testPerson2;
        EventModel[] eventsInput = new EventModel[3];
        eventsInput[0] = testEvent1;
        eventsInput[1] = testEvent2;
        eventsInput[2] = testEvent3;
        LoadRequest inputRequest = new LoadRequest();
        inputRequest.setUsers(usersInput);
        inputRequest.setEvents(eventsInput);
        inputRequest.setPersons(personsInput);


        LoadResult expectedResult = new LoadResult();
        expectedResult.setSuccess(true);
        expectedResult.setNumEvents(3);
        expectedResult.setNumPersons(2);
        expectedResult.setNumUsers(2);

        LoadResult outputResult = myLoadService.load(inputRequest);

        assertEquals(expectedResult, outputResult);
    }
}