package com.example.familymapclient;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    String maintsBirthID = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment loginFrag = fm.findFragmentById(R.id.firstLayout);

        if (!ClientModel.getInstance().isLoggedIn()){
            if (loginFrag == null) {
                loginFrag = LoginRegisterFragment.newInstance(this);
                fm.beginTransaction()
                        .add(R.id.firstLayout, loginFrag)
                        .commit();
            }
        }
    }

    public void onLoginRegisterSuccess(){
        ClientModel clientModel = ClientModel.getInstance();
        clientModel.setLoggedIn(true);


        String birthIDOfUser = clientModel.getPeopleEventMap().get(clientModel.getUser().getPersonID()).get(0).getEventID();

        maintsBirthID = birthIDOfUser;

        FragmentManager fm = getSupportFragmentManager();
    }

}
