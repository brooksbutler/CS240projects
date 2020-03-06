package Result;

import Model.PersonModel;
/**
 * A Result object for getting a person
 */
public class PersonIDResult {
    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;
    private transient boolean success;
    private transient String message;

    /**
     * Default constructor
     */
    public PersonIDResult(){
    }

    /**
     * Public constructor from PersonModel object
     * @param p
     */
    public PersonIDResult(PersonModel p){
        this.associatedUsername = p.getUserName();
        this.personID = p.getPersonID();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.gender = p.getGender();
        this.fatherID = p.getFatherID();
        this.motherID = p.getMotherID();
        this.spouseID = p.getSpouseID();
    }

    public void setSuccess(boolean b){
        success = b;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setMessage(String s){
        message = s;
    }

    public void setUserName(String userName) { this.associatedUsername = userName; }

    public void setPersonID(String personID) { this.personID = personID; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setGender(String gender) { this.gender = gender; }

    public void setFatherID(String fatherID) { this.fatherID = fatherID; }

    public void setMotherID(String motherID) { this.motherID = motherID; }

    public void setSpouseID(String spouseID) { this.spouseID = spouseID; }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o == null) { return false; }
        if (getClass() != o.getClass()) { return false; }

        PersonIDResult secondPerson = (PersonIDResult) o;

        if (personID!= null && !personID.equals(secondPerson.personID)) { return false; }
        if (associatedUsername!= null && !associatedUsername.equals(secondPerson.associatedUsername)) { return false; }
        if (firstName != null && !firstName.equals(secondPerson.firstName)) { return false; }
        if (lastName != null && !lastName.equals(secondPerson.lastName)) { return false; }
        if (gender != null && !gender.equals(secondPerson.gender)) { return false; }
        if (fatherID != null && !fatherID.equals(secondPerson.fatherID)) { return false; }
        if (motherID != null && !motherID.equals(secondPerson.motherID)) { return false; }
        if (spouseID != null && !spouseID.equals(secondPerson.spouseID)) { return false; }
        if (success != secondPerson.success) { return false; }
        if (message == null && secondPerson.message != null) { return false; }
        if (message != null && secondPerson.message == null){ return false; }
        if (message != null && secondPerson.message != null){
            if (!message.equals(secondPerson.message)){ return false; }
        }
        return true;
    }
}
