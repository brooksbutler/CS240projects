package DataAccessObjects;

import Model.UserModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private Database db;
    private UserDAO myUserDAO;
    private UserModel bestUser;

    @BeforeEach
    public void setUp() throws Exception {
        myUserDAO = new UserDAO();
        db = new Database();
        db.openConnection();
        db.resetTables();
        myUserDAO = db.getMyUserDAO();

        bestUser = new UserModel();
        bestUser.setUserName("realUserName123");
        bestUser.setPassword("abc123");
        bestUser.setEmail("thisIsARealEmail@totallyreal.com");
        bestUser.setFirstName("John");
        bestUser.setLastName("Doe");
        bestUser.setGender("m");
        bestUser.setPersonID(UUID.randomUUID().toString());

    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.resetTables();
        db.closeConnection(true);
    }

    @Test
    void insertUserPass() throws Database.DatabaseException {
        UserModel compareTest = null;

        try {
            myUserDAO.insertUser(bestUser);

            compareTest = myUserDAO.getUserModel(bestUser.getUserName());
            db.closeConnection(true);
        } catch (Database.DatabaseException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);

        assertEquals(bestUser, compareTest);
    }

    @Test
    void insertUserFail() throws Database.DatabaseException {
        boolean didItWork = true;
        try {
            myUserDAO.insertUser(bestUser);

            myUserDAO.insertUser(bestUser);
            db.closeConnection(true);
        } catch (Database.DatabaseException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);

        UserModel compareTest = bestUser;
        db.openConnection();
        try {
            compareTest = myUserDAO.getUserModel(bestUser.getUserName());
            db.closeConnection(true);
        } catch (Database.DatabaseException e) {
            db.closeConnection(false);
        }
        //Now make sure that compareTest is indeed null
        assertNull(compareTest);
    }


    @Test
    void getUserModelPass() throws Database.DatabaseException {
        UserModel findTest1 = null;
        UserModel findTest2 = null;
        UserModel findTest3 = null;
        UserModel findTest4 = null;

        UserModel otherUser = null;
        UserModel anotherUser = null;

        try{
            myUserDAO.insertUser(bestUser);

            otherUser = new UserModel();
            otherUser.setUserName("otherRealUserName123");
            otherUser.setPassword("abc123");
            otherUser.setEmail("otherRealEmail@totallyreal.com");
            otherUser.setFirstName("Jane");
            otherUser.setLastName("Doe");
            otherUser.setGender("f");
            otherUser.setPersonID(UUID.randomUUID().toString());
            myUserDAO.insertUser(otherUser);

            anotherUser = new UserModel();
            anotherUser.setUserName("anotherRealUserName123");
            anotherUser.setPassword("abc123");
            anotherUser.setEmail("anotherRealEmail@totallyreal.com");
            anotherUser.setFirstName("Jack");
            anotherUser.setLastName("Doe");
            anotherUser.setGender("m");
            anotherUser.setPersonID(UUID.randomUUID().toString());
            myUserDAO.insertUser(anotherUser);

            String nonUserID = UUID.randomUUID().toString();

            findTest1 = myUserDAO.getUserModel(bestUser.getUserName());
            findTest2 = myUserDAO.getUserModel(otherUser.getUserName());
            findTest3 = myUserDAO.getUserModel(anotherUser.getUserName());
            findTest4 = myUserDAO.getUserModel(nonUserID);

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
    void getUserModelFail() throws Database.DatabaseException {
        boolean didItWork1 = true;
        boolean didItWork2 = true;
        try {

            myUserDAO.insertUser(bestUser);

            String fakeUser = UUID.randomUUID().toString();
            UserModel test1 = myUserDAO.getUserModel(null);
            UserModel test2 = myUserDAO.getUserModel(fakeUser);

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
        UserModel resetTest = bestUser;
        try {
            myUserDAO.insertUser(bestUser);
            db.closeConnection(true);

            db.openConnection();
            db.resetTables();
            db.closeConnection(true);

            db.openConnection();
            resetTest = myUserDAO.getUserModel(bestUser.getUserName());
            db.closeConnection(true);
        }catch (Database.DatabaseException e){
            db.closeConnection(false);
        }
        assertNull(resetTest);
    }

//    @Test
//    void setConnection() {
//    }
//
//
//
//    @Test
//    void tableToString() {
//    }
//
//    @Test
//    void doesUserNameExist() {
//    }
//
//    @Test
//    void deleteUser() {
//    }
//
//    @Test
//    void doUsernameAndPasswordExist() {
//    }
//
//    @Test
//    void getPersonIDOfUser() {
//    }
}