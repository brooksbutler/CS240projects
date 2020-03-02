package Services;

import DataAccessObjects.Database;
import DataAccessObjects.EventDAO;
import DataAccessObjects.PersonDAO;
import DataAccessObjects.UserDAO;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import ObjectCodeDecode.Decoder;
import ObjectCodeDecode.IndividualLocation;
import ObjectCodeDecode.LocationArray;
import ObjectCodeDecode.StringArray;
import Result.FillResult;

import java.util.Random;
import java.util.UUID;

/**
 *A service object which generates an FillResult object
 *<pre>
 *
 *</pre>
 */
public class FillService {
    private Database myDB;
    private String[] femaleNames;
    private String[] maleNames;
    private String[] lastNames;
    private LocationArray locationArray;

    /**
     * Public constructor
     */
    public FillService(){
        myDB = new Database();

        femaleNames = new String[147];
        maleNames = new String[142];
        lastNames = new String[150];
        locationArray = Decoder.decodeLocations("json/locations.json");

        StringArray temp = Decoder.decodeNames("json/fnames.json"); //gson.fromJson(new FileReader("fnames.json"), StringArray.class);
        StringArray temp2 = Decoder.decodeNames("json/mnames.json"); //gson.fromJson(new FileReader("mnames.json"), StringArray.class);
        StringArray temp3 = Decoder.decodeNames("json/snames.json"); //  gson.fromJson(new FileReader("snames.json"), StringArray.class);

        for (int i = 0; i < 147; i++){
            assert temp != null;
            femaleNames[i] = temp.getValueAt(i);
        }

        for (int i = 0; i < 142; i++){
            assert temp2 != null;
            maleNames[i] = temp2.getValueAt(i);
        }

        for (int i = 0; i < 150; i++){
            assert temp3 != null;
            lastNames[i] = temp3.getValueAt(i);
        }
    }

    /**
     *
     * @param userName
     * @param numGenerations
     * @return FillResult object
     */
    public FillResult fill(String userName, int numGenerations){
        FillResult response = new FillResult();

        try{
            myDB.openConnection();

            UserDAO myUserDAO = myDB.getMyUserDAO();
            EventDAO myEventDAO = myDB.getMyEventDAO();
            PersonDAO myPersonDAO = myDB.getMyPersonDAO();

            if (!myUserDAO.doesUserNameExist(userName)){
                throw new Database.DatabaseException("username does not exist");
            }
            UserModel user = myUserDAO.getUserModel(userName); //user does not have the same personID as the userName user
            user.setPersonID(UUID.randomUUID().toString());

            myDB.deleteEverythingOfUser(user); //deletes all things of user, including the user
            myUserDAO.insertUser(user); //inserts same user but with new personID into the database
            PersonModel root = new PersonModel(user);  //creates a person representation of the user
            myPersonDAO.insertPerson(root); //inserts root into database

            int rootBirthYear = makeRootsEvents(root, myEventDAO); //make root's events

            //Now were going to give generateGenerations root, which generates fathers and mothers, then generates fathers and mothers events, and each father and mother is passed on to have its generations made

            if (numGenerations == -1){//default case
                generateGenerations(root, 4, myEventDAO, myPersonDAO, rootBirthYear); //default is four generations
                response.setNumPersons(31);
                response.setNumEvents(124);
                response.setSuccess(true);
            } else {
                generateGenerations(root, numGenerations, myEventDAO, myPersonDAO, rootBirthYear);
                double numG = (double) numGenerations;
                double answer = (Math.pow(2.0, (numG + 1.0)) - 1.0);
                int finalAnswer = (int) answer;
                response.setNumPersons(finalAnswer);
                response.setNumEvents(finalAnswer * 4);
                response.setSuccess(true);
            }

            myDB.closeConnection(true);

        } catch (Database.DatabaseException e){
            response.setSuccess(false);
            response.setMessage(e.getMessage());

            try{
                myDB.closeConnection(false);
            }catch (Database.DatabaseException d){
                response.setSuccess(false);
                response.setMessage(d.getMessage());
            }
        }
        return response;
    }

    /**
     * Makes rot events for a person
     * @param root
     * @return
     * @throws Database.DatabaseException
     */
    public int makeRootsEvents(PersonModel root, EventDAO myEventDAO) throws Database.DatabaseException {
        int rootBirthYear = 1960;

        //making root's birth
        Random rand = new Random();
        int r = rand.nextInt(977);
        IndividualLocation randLocation = locationArray.getLocations()[r];
        EventModel birth = new EventModel(UUID.randomUUID().toString(),root.getUserName(),root.getPersonID(),
                randLocation.getLatitude(),randLocation.getLongitude(), randLocation.getCountry(),randLocation.getCity(),
                "Birth",rootBirthYear);

        myEventDAO.insertEvent(birth); //inserts birth into database
        return rootBirthYear;
    }

    /**
     * Generates a fake generation for a person
     * @param orphan
     * @param numGenerations
     * @param myEventDAO
     * @param orphanBirthYear
     * @throws Database.DatabaseException
     */
    public void generateGenerations(PersonModel orphan,
                                    int numGenerations,
                                    EventDAO myEventDAO,
                                    PersonDAO myPersonDAO,
                                    int orphanBirthYear) throws Database.DatabaseException { //recursive, receives person whose parent's need to be generated
        PersonModel mother = makeMother(orphan, myPersonDAO); //makes orphan's mother
        PersonModel father = makeFather(orphan, myPersonDAO); //makes orphan's father
        myPersonDAO.updateSpouse(father, mother.getPersonID()); //adds mother to be father's spouse
        myPersonDAO.updateSpouse(mother, father.getPersonID());  //adds father to be mother's spouse

        //Now make events for parents, root's events were taken care of in register, in other cases, orphan would be a father or mother already.

        int birthDateOfBoth = generateEventDataParents(mother, father, orphanBirthYear, myEventDAO); //Generates events for parents


        numGenerations--;
        if (numGenerations > 0){
            generateGenerations(mother, numGenerations, myEventDAO, myPersonDAO, birthDateOfBoth);
            generateGenerations(father, numGenerations,myEventDAO, myPersonDAO, birthDateOfBoth);
        }
    }

    public PersonModel makeMother(PersonModel orphan, PersonDAO myPersonDAO) throws Database.DatabaseException {
        //Orphan will always have a personID already, even user
        Random rand = new Random();
        int r = rand.nextInt(146);

        String motherID = UUID.randomUUID().toString();
        String descedantOfMother = orphan.getUserName();
        String motherFirstName = femaleNames[r];
        r = rand.nextInt(149);
        String motherLastName = lastNames[r];
        String gender = "f";
        //Updates orphan's mother
        myPersonDAO.updateMother(orphan, motherID);
        //Make mother model
        PersonModel mother = new PersonModel(motherID,descedantOfMother,motherFirstName,motherLastName,gender);

        //insert mothermodel into table
        myPersonDAO.insertPerson(mother);

        return mother;
    }

    public PersonModel makeFather(PersonModel orphan, PersonDAO myPersonDAO) throws Database.DatabaseException {
        //Orphan will always have a personID already, even user
        Random rand = new Random();
        int r = rand.nextInt(141);

        String fatherID = UUID.randomUUID().toString();
        String descedantOfFather = orphan.getUserName();
        String fatherFirstName = maleNames[r];
        String fatherLastName = orphan.getLastName();
        String gender = "m";

        //Updates orphan's father
        myPersonDAO.updateFather(orphan, fatherID);

        //Make father model
        PersonModel father = new PersonModel(fatherID,descedantOfFather,fatherFirstName,fatherLastName,gender);
        //insert father model into table
        myPersonDAO.insertPerson(father);

        return father;
    }

    public int generateEventDataParents(PersonModel mother, PersonModel father, int orphanBirthYear,
                                        EventDAO myEventDAO) throws Database.DatabaseException { //not recursive but will make 4 events for the given person, for now just birth
        Random rand = new Random();
        int parentsBirthDate = orphanBirthYear - 26;

        int r = rand.nextInt(977);
        IndividualLocation randLocation = locationArray.getLocations()[r];
        EventModel motherBirth = new EventModel(UUID.randomUUID().toString(),mother.getUserName(),
                mother.getPersonID(),randLocation.getLatitude(),randLocation.getLongitude(),
                randLocation.getCountry(),randLocation.getCity(),"Birth",parentsBirthDate); //making mothers's birth
        myEventDAO.insertEvent(motherBirth); //inserted mother's birth

        r = rand.nextInt(977);
        randLocation = locationArray.getLocations()[r];
        EventModel fatherBirth = new EventModel(UUID.randomUUID().toString(),father.getUserName(),
                father.getPersonID(),randLocation.getLatitude(),randLocation.getLongitude(),
                randLocation.getCountry(),randLocation.getCity(),"Birth",parentsBirthDate); //making mothers's birth
        myEventDAO.insertEvent(fatherBirth); //inserted father's birth

        r = rand.nextInt(977);
        randLocation = locationArray.getLocations()[r];
        EventModel motherDeath = new EventModel(UUID.randomUUID().toString(),mother.getUserName(),
                mother.getPersonID(),randLocation.getLatitude(),randLocation.getLongitude(),
                randLocation.getCountry(),randLocation.getCity(),"Death",orphanBirthYear+56); //making mothers's birth
        myEventDAO.insertEvent(motherDeath); //inserted mother's death

        r = rand.nextInt(977);
        randLocation = locationArray.getLocations()[r];
        EventModel fatherDeath = new EventModel(UUID.randomUUID().toString(),father.getUserName(),
                father.getPersonID(),randLocation.getLatitude(),randLocation.getLongitude(),
                randLocation.getCountry(),randLocation.getCity(),"Death",orphanBirthYear+56);
        myEventDAO.insertEvent(fatherDeath); //inserted father's death

        r = rand.nextInt(977);
        randLocation = locationArray.getLocations()[r];
        EventModel motherMarraige = new EventModel(UUID.randomUUID().toString(),mother.getUserName(),
                mother.getPersonID(),randLocation.getLatitude(),randLocation.getLongitude(),
                randLocation.getCountry(),randLocation.getCity(),"Marriage",orphanBirthYear-2);
        myEventDAO.insertEvent(motherMarraige); //inserted mother's death

        r = rand.nextInt(977);
        randLocation = locationArray.getLocations()[r];
        EventModel fatherMarraige = new EventModel(UUID.randomUUID().toString(),father.getUserName(),
                father.getPersonID(),randLocation.getLatitude(),randLocation.getLongitude(),
                randLocation.getCountry(),randLocation.getCity(),"Marriage",orphanBirthYear-2);
        myEventDAO.insertEvent(fatherMarraige);

        return parentsBirthDate;
    }
}
