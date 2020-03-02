package Result;

import Model.AuthTokenModel;
/**
 * A Result object for registering
 */
public class RegisterResult {
    private String authToken;
    private String userName;
    private String personId;
    private transient boolean success;
    private transient String message;

    /**
     * Default constructor
     */
    public RegisterResult(){
        success = false;
        authToken = "";
        userName = "";
        personId = "";
    }

    /**
     * Public constructor from authenticator token model object
     * @param a
     */
    public RegisterResult(AuthTokenModel a){
        authToken = a.getAuthToken();
        userName = a.getUserName();
        personId = a.getPersonID();
    }

    public void setAuthToken(String intput){
        this.authToken = intput;
    }

    public void setUserName(String input){
        this.userName = input;
    }

    public void setPersonId(String input){
        this.personId = input;
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

    public void setMessage(String s){
        message = s;
    }

    public String getAuthToken(){
        return authToken;
    }

    public String getPersonID(){
        return personId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }

        RegisterResult secondResult = (RegisterResult) o;

        if(!authToken.equals(secondResult.authToken)){ return false; }
        if(!userName.equals(secondResult.userName)){ return false; }
        if(!personId.equals(secondResult.personId)){ return false; }
        if(success != secondResult.success) { return false; }
        if(message == null && secondResult.message != null) { return false; }
        if (message != null && secondResult.message == null){ return false; }
        if (message != null && secondResult.message != null){
            if (!message.equals(secondResult.message)){ return false; }
        }
        return true;
    }
}
