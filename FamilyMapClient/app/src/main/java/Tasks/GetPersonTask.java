package Tasks;

import android.content.Context;
import android.os.AsyncTask;
import androidx.fragment.app.Fragment;
import android.widget.Toast;

import com.example.familymapclient.MainActivity;
import com.example.familymapclient.R;
import com.example.familymapclient.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import Model.PersonModel;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.PersonIDResult;
import Result.RegisterResult;

/**
 * Created by pjohnst5 on 11/17/17.
 */

public class GetPersonTask extends AsyncTask<String, Void, PersonIDResult> {
    PersonIDResult person;
    Fragment myFrag;
    Context whereICameFrom;
    LoginRequest loginRequest;
    LoginResult loginResult;
    RegisterRequest requestForGetPeopleTask;
    RegisterResult resultForGetPeopleTask;

    public GetPersonTask(Fragment in, Context inTwo, LoginRequest inThree, LoginResult inFour){
        myFrag = in;
        whereICameFrom = inTwo;
        loginRequest = inThree;
        requestForGetPeopleTask = new RegisterRequest();
        requestForGetPeopleTask.setServerHost(loginRequest.getServerHost());
        requestForGetPeopleTask.setServerPort(loginRequest.getServerPort());
        resultForGetPeopleTask = new RegisterResult();
        loginResult = inFour;
    }

    public PersonIDResult doInBackground(String... urlAuth){
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        try {
            ServerProxy myServerProxy = new ServerProxy();
            URL url = new URL(urlAuth[0]);

            person = myServerProxy.getPersonURL(url, urlAuth[1]);

            return person;

        } catch (MalformedURLException e){
            person.setMessage("Bad URl");
            person.setSuccess(false);
            return person;
        }
    }

    protected void onPostExecute(PersonIDResult result){

        if (result.getSuccess()){

            String stringForToastIfSuccessful = new String("Login Success!" + "\n" + result.getFirstName() + "\n" + result.getLastName());
            resultForGetPeopleTask.setAuthToken(loginResult.getAuthToken());
            String url = new String ("http://" + requestForGetPeopleTask.getServerHost() + ":" + requestForGetPeopleTask.getServerPort() + "/person/");

            GetPeopleTask myPeopleTask = new GetPeopleTask(myFrag,requestForGetPeopleTask, resultForGetPeopleTask, whereICameFrom, stringForToastIfSuccessful);
            myPeopleTask.execute(url, resultForGetPeopleTask.getAuthToken());
        }
        else {
            Toast.makeText(myFrag.getContext(),
                    R.string.loginNotSuccessfulPerson,
                    Toast.LENGTH_SHORT).show();
        }

    }


}