package DataAccessObjects;

import Model.EventModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class EventDAOTest {
    private Database db;
    private EventDAO myEventDAO;
    private EventModel bestEvent;

    @BeforeEach
    public void setUp() throws Exception {
        myEventDAO = new EventDAO();
        db = new Database();
        db.openConnection();
        db.resetTables();
        myEventDAO = db.getMyEventDAO();
        bestEvent = new EventModel(UUID.randomUUID().toString(),"realUserName",UUID.randomUUID().toString(),
                420.69,69.420,"Real Country","Real City","eventType",1999);
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.resetTables();
        db.closeConnection(true);
    }

    @Test
    void insertEventPass() throws Database.DatabaseException {
        EventModel compareTest = null;

        try {
            myEventDAO.insertEvent(bestEvent);

            compareTest = myEventDAO.selectSingleEvent(bestEvent.getEventID());
            db.closeConnection(true);
        } catch (Database.DatabaseException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);

        assertEquals(bestEvent, compareTest);
    }

    @Test
    void insertEventFail() throws Database.DatabaseException {
        boolean didItWork = true;
        try {
            myEventDAO.insertEvent(bestEvent);

            myEventDAO.insertEvent(bestEvent);
            db.closeConnection(true);
        } catch (Database.DatabaseException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);

        EventModel compareTest = bestEvent;
        db.openConnection();
        try {
            compareTest = myEventDAO.selectSingleEvent(bestEvent.getEventID());
            db.closeConnection(true);
        } catch (Database.DatabaseException e) {
            db.closeConnection(false);
        }
        //Now make sure that compareTest is indeed null
        assertNull(compareTest);
    }

    @Test
    void selectSingleEventPass() throws Database.DatabaseException {
        EventModel findTest1 = null;
        EventModel findTest2 = null;
        EventModel findTest3 = null;
        EventModel findTest4 = null;

        EventModel otherEvent = null;
        EventModel anotherEvent = null;

        try{
            myEventDAO.insertEvent(bestEvent);

            otherEvent = new EventModel(UUID.randomUUID().toString(),"otherRealUserName", UUID.randomUUID().toString(),
                    69.420,69.420,"Other Real Country","Other Real City","otherEventType",1998);

            myEventDAO.insertEvent(otherEvent);

            anotherEvent = new EventModel(UUID.randomUUID().toString(),"anotherRealUserName",UUID.randomUUID().toString(),
                    69.420,69.420,"Another Real Country","Another Real City","anotherEventType",1998);

            myEventDAO.insertEvent(anotherEvent);

            String nonEventID = UUID.randomUUID().toString();

            findTest1 = myEventDAO.selectSingleEvent(bestEvent.getEventID());
            findTest2 = myEventDAO.selectSingleEvent(otherEvent.getEventID());
            findTest3 = myEventDAO.selectSingleEvent(anotherEvent.getEventID());
            findTest4 = myEventDAO.selectSingleEvent(nonEventID);

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
    void selectSingleEventFail() throws Database.DatabaseException {
        boolean didItWork1 = true;
        boolean didItWork2 = true;
        try {

            myEventDAO.insertEvent(bestEvent);

            String fakeEvent = UUID.randomUUID().toString();
            EventModel test1 = myEventDAO.selectSingleEvent(null);
            EventModel test2 = myEventDAO.selectSingleEvent(fakeEvent);

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
        EventModel resetTest = bestEvent;
        try {
            myEventDAO.insertEvent(bestEvent);
            db.closeConnection(true);

            db.openConnection();
            db.resetTables();
            db.closeConnection(true);

            db.openConnection();
            resetTest = myEventDAO.selectSingleEvent(bestEvent.getEventID());
            db.closeConnection(true);
        }catch (Database.DatabaseException e){
            db.closeConnection(false);
        }
        assertNull(resetTest);
    }
}