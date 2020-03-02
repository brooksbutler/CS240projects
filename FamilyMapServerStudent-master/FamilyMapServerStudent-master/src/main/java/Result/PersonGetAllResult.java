package Result;

import Model.PersonModel;
/**
 * A Result object for getting all persons
 */
public class PersonGetAllResult {
    private transient String message;
    private PersonIDResult[] data;
    private transient boolean success;

    /**
     * Public constructor
     */
    public PersonGetAllResult(){
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PersonIDResult[] getData() {
        return data;
    }

    public void setData(PersonIDResult[] input) {
        data = new PersonIDResult[input.length];
        System.arraycopy(input, 0, data, 0, input.length);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o == null) { return false; }
        if (this.getClass() != o.getClass()) { return false; }

        PersonGetAllResult secondResult = (PersonGetAllResult) o;

        if (success != secondResult.success) { return false; }
        if (message == null && secondResult.message != null) { return false; }
        if (message != null && secondResult.message == null){ return false; }
        if (message != null && secondResult.message != null){
            if (!message.equals(secondResult.message)){ return false; }
        }
        if (data != null){
            for (int i = 0; i < data.length; i++){
                if (!data[i].equals(secondResult.data[i])){ return false; }
            }
        }
        return true;
    }
}
