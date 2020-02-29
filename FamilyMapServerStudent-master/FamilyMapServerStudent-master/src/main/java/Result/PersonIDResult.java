package Result;

import Model.PersonModel;
/**
 * A Result object for getting a person
 */
public class PersonIDResult {
    private String descendant;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String father;
    private String mother;
    private String spouse;
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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
