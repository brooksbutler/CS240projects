package DataAccessObjects;

import Model.AuthTokenModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDAOTest {
    private Database db;
    private AuthTokenDAO myAuthTokenDAO;
    private AuthTokenModel bestAuthToken;

    @BeforeEach
    public void setUp() throws Exception {
        myAuthTokenDAO = new AuthTokenDAO();
        db = new Database();
        db.openConnection();
        db.resetTables();
        myAuthTokenDAO = db.getMyAuthTokenDAO();
        bestAuthToken = new AuthTokenModel(UUID.randomUUID().toString(),"realUserName",UUID.randomUUID().toString());
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.resetTables();
        db.closeConnection(true);
    }

    @Test
    void insertTokenPass() throws Database.DatabaseException {
        AuthTokenModel compareTest = null;

        try {
            myAuthTokenDAO.insertToken(bestAuthToken);

            compareTest = myAuthTokenDAO.getAuthTokenModel(bestAuthToken.getAuthToken());
            db.closeConnection(true);
        } catch (Database.DatabaseException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);

        assertEquals(bestAuthToken, compareTest);
    }


    @Test
    void insertTokenFail() throws Database.DatabaseException {
        boolean didItWork = true;
        try {
            myAuthTokenDAO.insertToken(bestAuthToken);

            myAuthTokenDAO.insertToken(bestAuthToken);
            db.closeConnection(true);
        } catch (Database.DatabaseException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);

        AuthTokenModel compareTest = bestAuthToken;
        db.openConnection();
        try {
            compareTest = myAuthTokenDAO.getAuthTokenModel(bestAuthToken.getAuthToken());
            db.closeConnection(true);
        } catch (Database.DatabaseException e) {
            db.closeConnection(false);
        }
        //Now make sure that compareTest is indeed null
        assertNull(compareTest);
    }

    @Test
    void getTokenPass() throws Database.DatabaseException {
        AuthTokenModel findTest1 = null;
        AuthTokenModel findTest2 = null;
        AuthTokenModel findTest3 = null;
        AuthTokenModel findTest4 = null;

        AuthTokenModel otherToken = null;
        AuthTokenModel anotherToken = null;

        try{
            myAuthTokenDAO.insertToken(bestAuthToken);

            otherToken = new AuthTokenModel(UUID.randomUUID().toString(),"otherRealUserName", UUID.randomUUID().toString());

            myAuthTokenDAO.insertToken(otherToken);

            anotherToken = new AuthTokenModel(UUID.randomUUID().toString(),"anotherRealUserName",UUID.randomUUID().toString());

            myAuthTokenDAO.insertToken(anotherToken);

            String nonToken = UUID.randomUUID().toString();

            findTest1 = myAuthTokenDAO.getAuthTokenModel(bestAuthToken.getAuthToken());
            findTest2 = myAuthTokenDAO.getAuthTokenModel(otherToken.getAuthToken());
            findTest3 = myAuthTokenDAO.getAuthTokenModel(anotherToken.getAuthToken());
            findTest4 = myAuthTokenDAO.getAuthTokenModel(nonToken);

            db.closeConnection(true);

        }catch (Database.DatabaseException e){
            db.closeConnection(false);
        }

        assertNotNull(findTest1);
        assertNotNull(findTest2);
        assertNotNull(findTest3);
        assertNull(findTest4);
    }

    @Test
    void getTokenFail() throws Database.DatabaseException {
        boolean didItWork1 = true;
        boolean didItWork2 = true;
        try {

            myAuthTokenDAO.insertToken(bestAuthToken);

            String fakeToken = UUID.randomUUID().toString();
            AuthTokenModel test1 = myAuthTokenDAO.getAuthTokenModel(null);
            AuthTokenModel test2 = myAuthTokenDAO.getAuthTokenModel(fakeToken);

            if(test1 == null){ didItWork1=false; }
            if(test2 == null){ didItWork2=false; }

            db.closeConnection(true);
        } catch (Database.DatabaseException e) {
            db.closeConnection(false);
        }
        assertFalse(didItWork1);
        assertFalse(didItWork2);
    }

    @Test
    void resetTable() throws Database.DatabaseException {
        AuthTokenModel resetTest = bestAuthToken;
        try {
            myAuthTokenDAO.insertToken(bestAuthToken);
            db.closeConnection(true);

            db.openConnection();
            db.resetTables();
            db.closeConnection(true);

            db.openConnection();
            resetTest = myAuthTokenDAO.getAuthTokenModel(bestAuthToken.getAuthToken());
            db.closeConnection(true);
        }catch (Database.DatabaseException e){
            db.closeConnection(false);
        }
        assertNull(resetTest);
    }
}