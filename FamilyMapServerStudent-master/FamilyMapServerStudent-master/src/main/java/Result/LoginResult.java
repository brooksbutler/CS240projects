package Result;

import Model.AuthTokenModel;
/**
 * A Result object for logging in
 */
public class LoginResult {

    private String authToken;
    private String userName;
    private String personId;
    private transient boolean success;
    private transient String message;

    /**
     * Default constructor
     */
    public LoginResult(){
    }

    /**
     * Public constructor from authenticator token
     * @param a
     */
    public LoginResult(AuthTokenModel a){
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
        this.personId = input;
    }

    public String getAuthToken(){
        return authToken;
    }

    public String getPersonId(){return personId;}

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
