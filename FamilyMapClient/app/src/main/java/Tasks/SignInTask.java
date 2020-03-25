package Tasks;


import android.content.Context;
import android.os.AsyncTask;
import androidx.fragment.app.Fragment;
import android.widget.Toast;

import com.example.familymapclient.R;
import com.example.familymapclient.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import Request.LoginRequest;
import Result.LoginResult;

public class SignInTask extends AsyncTask<LoginRequest, Void, LoginResult> {

    private LoginResult answer = new LoginResult();
    private LoginRequest myRequest;
    private Fragment myFrag;
    private Context myMainActivity;

    public SignInTask(Fragment in, Context inActivity){
        this.myFrag = in;
        myMainActivity = inActivity;
    }



    public LoginResult doInBackground(LoginRequest... myLoginRequest) {
        myRequest = myLoginRequest[0];
        try {
            ServerProxy myProxy = new ServerProxy();

            URL url = new URL("http://" + myLoginRequest[0].getServerHost() + ":" + myLoginRequest[0].getServerPort() + "/user/login");
            answer = myProxy.getLoginUrl(url, myLoginRequest[0]);

            return answer;

        } catch (MalformedURLException e){
            answer.setMessage("Bad URl");
            answer.setSuccess(false);
            return answer;
        }
    }

    protected void onPostExecute(LoginResult result) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        if (answer.getSuccess()){
            String url = new String("http://" + myRequest.getServerHost() + ":" + myRequest.getServerPort() + "/person/" + result.getPersonId());
            GetPersonTask personTask = new GetPersonTask(myFrag, myMainActivity,myRequest, result);
            personTask.execute(url, result.getAuthToken());

        } else {
            Toast.makeText(myFrag.getContext(),
                    R.string.loginNotSuccessful,
                    Toast.LENGTH_SHORT).show();
        }
    }
}