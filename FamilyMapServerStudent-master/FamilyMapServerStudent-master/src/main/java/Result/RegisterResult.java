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
    }

    /**
     * Public constructor from authenticator token model object
     * @param a
     */
    public RegisterResult(AuthTokenModel a){
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
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
