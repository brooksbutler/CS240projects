package DataAccessObjects;

import Model.PersonModel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PersonDAOTest {

    private Database db;
    private PersonDAO myPersonDAO;
    private PersonModel bestPerson;

    @BeforeEach
    public void setUp() throws Exception {
        myPersonDAO = new PersonDAO();
        db = new Database();
        db.openConnection();
        db.resetTables();
        myPersonDAO = db.getMyPersonDAO();

        bestPerson = new PersonModel();
        bestPerson.setPersonID(UUID.randomUUID().toString());
        bestPerson.setUserName("realUserName123");
        bestPerson.setFirstName("John");
        bestPerson.setLastName("Doe");
        bestPerson.setGender("m");
        bestPerson.setFatherID(UUID.randomUUID().toString());
        bestPerson.setMotherID(UUID.randomUUID().toString());
        bestPerson.setSpouseID(UUID.randomUUID().toString());
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.resetTables();
        db.closeConnection(true);
    }

    @Test
    void insertPersonPass() throws Database.DatabaseException{
        PersonModel compareTest = null;
        try {
            myPersonDAO.insertPerson(bestPerson);

            compareTest = myPersonDAO.selectSinglePerson(bestPerson.getPersonID());
            db.closeConnection(true);
        } catch (Database.DatabaseException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);

        assertEquals(bestPerson, compareTest);
    }

    @Test
    void insertPersonFail() throws Database.DatabaseException {
        boolean didItWork = true;
        try {
            myPersonDAO.insertPerson(bestPerson);

            myPersonDAO.insertPerson(bestPerson);
            db.closeConnection(true);
        } catch (Database.DatabaseException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);

        PersonModel compareTest = bestPerson;
        db.openConnection();
        try {
            compareTest = myPersonDAO.selectSinglePerson(bestPerson.getPersonID());
            db.closeConnection(true);
        } catch (Database.DatabaseException e) {
            db.closeConnection(false);
        }
        //Now make sure that compareTest is indeed null
        assertNull(compareTest);
    }

    @Test
    void selectSinglePersonPass() throws Database.DatabaseException {
        PersonModel findTest1 = null;
        PersonModel findTest2 = null;
        PersonModel findTest3 = null;
        PersonModel findTest4 = null;

        PersonModel otherPerson = null;
        PersonModel anotherPerson = null;
        try{
            myPersonDAO.insertPerson(bestPerson);

            otherPerson = new PersonModel();
            otherPerson.setPersonID(UUID.randomUUID().toString());
            otherPerson.setUserName("otherUserName123");
            otherPerson.setFirstName("Jane");
            otherPerson.setLastName("Doe");
            otherPerson.setGender("f");
            otherPerson.setFatherID(UUID.randomUUID().toString());
            otherPerson.setMotherID(UUID.randomUUID().toString());
            otherPerson.setSpouseID(UUID.randomUUID().toString());
            myPersonDAO.insertPerson(otherPerson);

            anotherPerson = new PersonModel();
            anotherPerson.setPersonID(UUID.randomUUID().toString());
            anotherPerson.setUserName("anotherUserName123");
            anotherPerson.setFirstName("Jack");
            anotherPerson.setLastName("Doe");
            anotherPerson.setGender("m");
            anotherPerson.setFatherID(UUID.randomUUID().toString());
            anotherPerson.setMotherID(UUID.randomUUID().toString());
            anotherPerson.setSpouseID(UUID.randomUUID().toString());
            myPersonDAO.insertPerson(anotherPerson);

            String nonPersonID = UUID.randomUUID().toString();

            findTest1 = myPersonDAO.selectSinglePerson(bestPerson.getPersonID());
            findTest2 = myPersonDAO.selectSinglePerson(otherPerson.getPersonID());
            findTest3 = myPersonDAO.selectSinglePerson(anotherPerson.getPersonID());
            findTest4 = myPersonDAO.selectSinglePerson(nonPersonID);


            db.closeConnection(true);

        }catch (Database.DatabaseException e){
            db.closeConnection(false);
        }

        assertNotNull(findTest1);
        assertNotNull(findTest2);
        assertNotNull(findTest3);
        assertNull(findTest4);

        assertEquals(bestPerson, findTest1);
        assertEquals(otherPerson, findTest2);
        assertEquals(anotherPerson, findTest3);
    }

    @Test
    void selectSinglePersonFail() throws Database.DatabaseException {
        boolean didItWork1 = true;
        boolean didItWork2 = true;
        try {

            myPersonDAO.insertPerson(bestPerson);

            String fakePerson = UUID.randomUUID().toString();
            PersonModel test1 = myPersonDAO.selectSinglePerson(null);
            PersonModel test2 = myPersonDAO.selectSinglePerson(fakePerson);

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
        PersonModel resetTest = bestPerson;
        try {
            myPersonDAO.insertPerson(bestPerson);
            db.closeConnection(true);

            db.openConnection();
            db.resetTables();
            db.closeConnection(true);

            db.openConnection();
            resetTest = myPersonDAO.selectSinglePerson(bestPerson.getPersonID());
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
//    @Test
//    void updateParent() {
//    }
//
//    @Test
//    void updateSpouse() {
//    }
//
//    @Test
//    void generateGenerations() {
//    }
//
//    @Test
//    void makeParent() {
//    }
//
//    @Test
//    void selectAllPersons() {
//    }
//
//    @Test
//    void tableToString() {
//    }
//
//    @Test
//    void deleteAllPeopleOfUser() {
//    }
//
//    @Test
//    void doesPersonExist() {
//    }
}