package Tasks;

import android.content.Context;
import android.os.AsyncTask;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.familymapclient.ClientModel;
import com.example.familymapclient.R;
import com.example.familymapclient.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import Request.RegisterRequest;
import Result.EventGetAllResult;
import Result.PersonGetAllResult;
import Result.RegisterResult;

public class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResult> {

    private Fragment fragActivity;
    private Context myMainActivity;
    private RegisterRequest taskRequest;
    private RegisterResult answer = new RegisterResult();
    private PersonGetAllResult peopleAnswer = new PersonGetAllResult();
    private EventGetAllResult eventAnswer = new EventGetAllResult();

    public RegisterTask(Fragment fragAct, Context in){
        this.fragActivity = fragAct;
        this.myMainActivity = in;
    }

    public RegisterResult doInBackground(RegisterRequest... myRegisterRequests){
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
        taskRequest = myRegisterRequests[0];
        try {
            ServerProxy myServerProxy = new ServerProxy();

            URL urla = new URL("http://" + myRegisterRequests[0].getServerHost() + ":" + myRegisterRequests[0].getServerPort() + "/user/register");
            answer = myServerProxy.getRegisterUrl(urla, myRegisterRequests[0]);

/*
            String url = new String ("http://" + taskRequest.getServerHost() + ":" + taskRequest.getServerPort() + "/person/");
            GetPeopleTask peopleTast = new GetPeopleTask(fragActivity);
            fragActivity.getActivity().runOnUiThread(new Runnable);
*/

            return answer;

        } catch (MalformedURLException e){
            answer.setMessage("Bad URl");
            answer.setSuccess(false);
            return answer;
        }
    }

    protected void onPostExecute(RegisterResult response) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        if (response.getSuccess()){ //was a successful register

            String url = new String ("http://" + taskRequest.getServerHost() + ":" + taskRequest.getServerPort() + "/person/");
            String stringForToastIfSuccessful = new String("Register Success!" + "\n" + taskRequest.getFirstName() + "\n" + taskRequest.getLastName());


        } else { //was not a successful register
            //display failed register toast
            Toast.makeText(fragActivity.getContext(),
                    R.string.registerNotSuccessful,
                    Toast.LENGTH_SHORT).show();
        }

    }
}