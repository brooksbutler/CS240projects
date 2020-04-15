package Tasks;

import android.content.Context;
import android.os.AsyncTask;
import androidx.fragment.app.Fragment;
import android.widget.Toast;

import com.example.familymapclient.ClientModel;
import com.example.familymapclient.R;
import com.example.familymapclient.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import Request.RegisterRequest;
import Result.PersonGetAllResult;
import Result.RegisterResult;

public class GetPeopleTask extends AsyncTask<String,Void,PersonGetAllResult> {
    private Fragment myFrag;
    private RegisterRequest taskRequest;
    private RegisterResult responseFromRegister;
    private Context whereICameFrom;
    private String stringForToastIfSuccessful;
    private PersonGetAllResult myResult = new PersonGetAllResult();

    public GetPeopleTask(Fragment in, RegisterRequest inTwo, RegisterResult inThree, Context inFour, String inFive){
        this.myFrag = in;
        taskRequest = inTwo;
        responseFromRegister = inThree;
        whereICameFrom = inFour;
        stringForToastIfSuccessful = inFive;
    }

    public PersonGetAllResult doInBackground(String... urlAuth){
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        try {
            ServerProxy myServerProxy = new ServerProxy();
            URL url = new URL(urlAuth[0]);

            myResult = myServerProxy.getAllPeopleUrl(url, urlAuth[1]);

            return myResult;

        } catch (MalformedURLException e){
            myResult.setMessage("Bad URl");
            myResult.setSuccess(false);
            return myResult;
        }
    }

    protected void onPostExecute(PersonGetAllResult result) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        if (result.isSuccess()){
            ClientModel cm = ClientModel.getInstance();
            cm.setPeople(myResult.getData());

            GetEventsTask eventTask = new GetEventsTask(myFrag, taskRequest, whereICameFrom, stringForToastIfSuccessful);
            String url = new String("http://" + taskRequest.getServerHost() + ":" + taskRequest.getServerPort() + "/event/");
            eventTask.execute(url, responseFromRegister.getAuthToken());


        } else {
            Toast.makeText(myFrag.getContext(),
                    R.string.registerNotSuccessfulPeopleGetAll,
                    Toast.LENGTH_SHORT).show();
        }

    }

}
