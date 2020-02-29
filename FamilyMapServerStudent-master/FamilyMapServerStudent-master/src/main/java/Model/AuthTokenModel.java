package Model;

import java.util.UUID;


/**
 * Model object for authenticator tokens
 */
public class AuthTokenModel {

    private String authToken;
    private String personID;
    private String userName;

    /**
     * Defualt constructor
     */
    public AuthTokenModel(){
        authToken = new String();
        personID = new String();
        userName = new String();
    }

    /**
     * Public constructor that generates a unique ID token
     * @param u
     */
    public AuthTokenModel(UserModel u){
        authToken = UUID.randomUUID().toString();
        personID = u.getPersonID();
        userName = u.getUserName();
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public String getUserName() {
        return userName;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
