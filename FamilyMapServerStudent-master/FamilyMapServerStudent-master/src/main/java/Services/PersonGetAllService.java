package Services;
import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.Database;
import DataAccessObjects.PersonDAO;
import Model.AuthTokenModel;
import Model.PersonModel;
import Result.PersonGetAllResult;
import Result.PersonIDResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *An service object which generates an PersonGetAllResult object
 *<pre>
 *
 *</pre>
 */
public class PersonGetAllService {
    private Database myDB;

    /**
     * Public constructor
     */
    public PersonGetAllService(){
        myDB = new Database();
    }

    /**
     *
     * @param authToken
     * @return PersonGetAllResult object
     */
    public PersonGetAllResult personGetAll(String authToken){
        PersonGetAllResult myPersonGetALLResult = new PersonGetAllResult();

        try{
            myDB.openConnection();
            PersonDAO myPersonDAO = myDB.getMyPersonDAO();
            AuthTokenDAO myAuthDAO = myDB.getMyAuthTokenDAO();


            if(myAuthDAO.doesAuthTokenExist(authToken)){
                AuthTokenModel auth = myAuthDAO.getAuthTokenModel(authToken);
                myPersonGetALLResult.setData(selectAllPersons(auth.getUserName(), myDB.getConn()));
            }

            myDB.closeConnection(true);
            myPersonGetALLResult.setSuccess(true);

        } catch (Database.DatabaseException e){
            myPersonGetALLResult.setSuccess(false);
            myPersonGetALLResult.setMessage(e.getMessage());
            try{
                myDB.closeConnection(false);
            }catch (Database.DatabaseException d){
                myPersonGetALLResult.setSuccess(false);
                myPersonGetALLResult.setMessage(d.getMessage());
            }
        }
        return myPersonGetALLResult;
    }

    /**
     * Select all persons in the person table
     * @param userName
     * @return
     * @throws Database.DatabaseException
     */
    public PersonIDResult[] selectAllPersons(String userName, Connection conn) throws Database.DatabaseException{
        ArrayList<PersonIDResult> out = new ArrayList<PersonIDResult>();

        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from persons WHERE userName = '" + userName + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    PersonIDResult tempPerson = new PersonIDResult();
                    tempPerson.setPersonID(rs.getString(1));
                    tempPerson.setUserName(rs.getString(2));
                    tempPerson.setFirstName(rs.getString(3));
                    tempPerson.setLastName(rs.getString(4));
                    tempPerson.setGender(rs.getString(5));
                    tempPerson.setFatherID(rs.getString(6));
                    tempPerson.setMotherID(rs.getString(7));
                    tempPerson.setSpouseID(rs.getString(8));
                    out.add(tempPerson);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("error: selectAllPersons failed");
        }
        PersonIDResult[] outFinal = new PersonIDResult[out.size()];
        outFinal = out.toArray(outFinal);
        return outFinal;
    }
}
