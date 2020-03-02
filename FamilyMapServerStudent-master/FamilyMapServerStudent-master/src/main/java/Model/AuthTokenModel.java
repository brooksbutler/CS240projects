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

    }
    public AuthTokenModel(String authToken, String personID, String userName){
        this.authToken = authToken;
        this.personID = personID;
        this. userName = userName;
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
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }
        AuthTokenModel secondEvent = (AuthTokenModel) o;

        if (!authToken.equals(secondEvent.authToken)) { return false; }
        if (!userName.equals(secondEvent.userName)) { return false; }
        if (!personID.equals(secondEvent.personID)) { return false; }
        return true;
    }
}
