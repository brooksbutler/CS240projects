package Services;

import DataAccessObjects.*;
import Model.UserModel;
import Result.FillResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.Random;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest {
    private FillService myFillService;

    UserModel testUser1 = new UserModel("username1", "password1",
            "email1@email.com","John", "Doe", "m",
            UUID.randomUUID().toString());

    @BeforeEach
    public void setUp() {
        myFillService = new FillService();
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
        myFillService = null;
    }

    @Test
    public void testFill1(){
        Random rand = new Random();
        int r = rand.nextInt(5);
        double numG = (double) r;
        double numPeople = (Math.pow(2.0, (numG + 1.0)) - 1.0);
        int finalNumPeople = (int) numPeople; //final
        int finalNumEvents = (finalNumPeople * 4);


        FillResult expectedResult = new FillResult();
        expectedResult.setSuccess(true);
        expectedResult.setNumEvents(finalNumEvents);
        expectedResult.setNumPersons(finalNumPeople);

        FillResult outputResult = myFillService.fill(testUser1.getUserName(), r);
        assertEquals(expectedResult, outputResult);
    }

    @Test
    public void testFill2(){
        Random rand = new Random();
        int r = rand.nextInt(5);
        FillResult badFillExpectedResult = new FillResult();
        badFillExpectedResult.setSuccess(false);
        badFillExpectedResult.setMessage("no such username");

        FillResult badOutputResponse = myFillService.fill("bogus", r);

        assertEquals(badFillExpectedResult, badOutputResponse);
    }
}