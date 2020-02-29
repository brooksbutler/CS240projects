package Model;

import Request.RegisterRequest;
import Request.LoginRequest;
/**
 * Model object for users
 */
public class UserModel {

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    /**
     * Default constructor
     */
    public UserModel(){

    }

    /**
     * Public constructor from a register request
     * @param r
     */
    public UserModel(RegisterRequest r){

    }

    /**
     * Public constructor froma  login request
     * @param l
     */
    public UserModel(LoginRequest l){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }

        UserModel secondUser = (UserModel) o;

        System.out.println(userName);
        System.out.println(secondUser.userName);
        System.out.println(password);
        System.out.println(secondUser.password);
        System.out.println(firstName);
        System.out.println(secondUser.firstName);
        System.out.println(lastName);
        System.out.println(secondUser.lastName);
        System.out.println(gender);
        System.out.println(secondUser.gender);
        System.out.println(personID);
        System.out.println(secondUser.personID);


        if (!userName.equals(secondUser.userName)) { return false; }
        if (!password.equals(secondUser.password)) { return false; }
        if (email != null && !email.equals(secondUser.email)) { return false; }
        if (firstName != null && !firstName.equals(secondUser.firstName)) { return false; }
        if (lastName != null && !lastName.equals(secondUser.lastName)) { return false; }
        if (gender != null && !gender.equals(secondUser.gender)) { return false; }
        if (personID!= null && !personID.equals(secondUser.personID)) { return false; }

        System.out.println("It's true");
        return true;
    }
}
