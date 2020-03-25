package Result;

import Model.AuthTokenModel;
/**
 * A Result object for logging in
 */
public class LoginResult {

    private String authToken;
    private String userName;
    private String personID;
    private transient boolean success;
    private transient String message;

    /**
     * Default constructor
     */
    public LoginResult(){
        authToken = "";
        userName = "";
        personID = "";
        success = false;
        message = "";
    }

    /**
     * Public constructor from authenticator token
     * @param a
     */
    public LoginResult(AuthTokenModel a){
        this.authToken = a.getAuthToken();
        this.userName = a.getUserName();
        this.personID = a.getPersonID();
        success = true;
        message = "";
    }

    public void setSuccess(boolean b){
        success = b;
    }

    public void setMessage(String s){
        message = s;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public void setAuthToken(String input){
        this.authToken = input;
    }
    public void setUserName(String input){
        this.userName = input;
    }
    public void setPersonId(String input){
        this.personID = input;
    }

    public String getAuthToken(){
        return authToken;
    }

    public String getPersonId(){return personID;}

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }

        LoginResult secondResponse = (LoginResult) o;

        if(!userName.equals(secondResponse.userName)){ return false; }
        if(!authToken.equals(secondResponse.authToken)){ return false; }
        if(!personID.equals(secondResponse.personID)){ return false; }
        if (success != secondResponse.success) { return false; }
        if (message == null && secondResponse.message != null) { return false; }
        if (message != null && secondResponse.message == null){ return false; }
        if (message != null && secondResponse.message != null){
            if (!message.equals(secondResponse.message)){ return false; }
        }
        return true;
    }
}
