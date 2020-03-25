package Tasks;

import android.content.Context;
import android.os.AsyncTask;
import androidx.fragment.app.Fragment;
import android.widget.Toast;

import com.example.familymapclient.ClientModel;
import com.example.familymapclient.MainActivity;
import com.example.familymapclient.R;
import com.example.familymapclient.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import Request.RegisterRequest;
import Result.EventGetAllResult;


public class GetEventsTask extends AsyncTask<String,Void, EventGetAllResult> {
    private Fragment myFrag;
    private RegisterRequest taskRequest;
    private Context whereICameFrom;
    private String stringForToasIfSuccessful;
    private EventGetAllResult myResult = new EventGetAllResult();

    public GetEventsTask(Fragment in, RegisterRequest inTwo, Context inThree, String inFour){
        this.myFrag = in;
        taskRequest = inTwo;
        whereICameFrom = inThree;
        stringForToasIfSuccessful = inFour;
    }

    public EventGetAllResult doInBackground(String... urlAuth){
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        try {
            ServerProxy myServerProxy = new ServerProxy();
            URL url = new URL(urlAuth[0]);

            ClientModel cm = ClientModel.getInstance();
            cm.setAuthToken(urlAuth[1]);

            myResult = myServerProxy.getAllEventsUrl(url, urlAuth[1]);

            return myResult;

        } catch (MalformedURLException e){
            myResult.setMessage("Bad URl");
            myResult.setSuccess(false);
            return myResult;
        }
    }

    protected void onPostExecute(EventGetAllResult response) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        if (response.isSuccess()){ //was a successful EventGetAll
            ClientModel cm = ClientModel.getInstance();
            cm.setEvents(myResult.getData());

            Toast.makeText(myFrag.getContext(),
                    stringForToasIfSuccessful,
                    Toast.LENGTH_SHORT).show();

            //This is when we call onLoginRegisterSuccess for when we are both logging in and registering
            MainActivity source = (MainActivity) whereICameFrom;
            source.onLoginRegisterSuccess();

        } else { //was not a successful eventGetAll
            //display failed eventGetAll toast
            Toast.makeText(myFrag.getContext(),
                    R.string.registerNotSuccessfulEventGetALL,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
