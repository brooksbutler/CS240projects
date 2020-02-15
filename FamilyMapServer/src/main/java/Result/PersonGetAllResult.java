package Result;

import Model.PersonModel;
/**
 * A Result object for getting all persons
 */
public class PersonGetAllResult {
    private transient String message;
    private PersonModel[] data;
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

    public PersonModel[] getData() {
        return data;
    }

    public void setData(PersonModel[] data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
