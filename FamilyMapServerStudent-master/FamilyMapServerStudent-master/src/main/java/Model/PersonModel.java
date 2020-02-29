package Model;

/**
 * Model object for persons
 */
public class PersonModel {

    private String personID;
    private String userName;
    private String firstName;
    private String lastName;
    private String gender;
    private String motherID;
    private String fatherID;
    private String spouseID;

    /**
     * Default constructor
     */
    public PersonModel(){
    }

    /**
     * Public constructor for generating a person for a user
     * @param u
     */
    public PersonModel(UserModel u){
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o == null) { return false; }
        if (getClass() != o.getClass()) { return false; }

        PersonModel secondPerson = (PersonModel) o;

        if (!personID.equals(secondPerson.personID)) { return false; }
        if (!userName.equals(secondPerson.userName)) { return false; }
        if (!firstName.equals(secondPerson.firstName)) { return false; }
        if (!lastName.equals(secondPerson.lastName)) { return false; }
        if (!gender.equals(secondPerson.gender)) { return false; }
        if (fatherID != null && !fatherID.equals(secondPerson.fatherID)) { return false; }
        if (motherID != null && !motherID.equals(secondPerson.motherID)) { return false; }
        if (spouseID != null && !spouseID.equals(secondPerson.spouseID)) { return false; }
        return true;
    }
}
