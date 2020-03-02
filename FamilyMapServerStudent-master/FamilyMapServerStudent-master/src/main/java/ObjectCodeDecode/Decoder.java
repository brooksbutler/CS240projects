package ObjectCodeDecode;


import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import Model.PersonModel;
import Request.*;
import Result.EventGetAllResult;
import Result.LoginResult;
import Result.PersonGetAllResult;
import Result.PersonIDResult;
import Result.RegisterResult;

public class Decoder {

    public Decoder(){
        //To be implemented later
    }

    public static LoginRequest decodeLoginRequest(Reader json){
        LoginRequest out;
        Gson gson = new Gson();

        out = gson.fromJson(json, LoginRequest.class);
        return out;
    }

    public static LoadRequest decodeLoadRequest(Reader json)  {
        LoadRequest out;
        Gson gson = new Gson();

        out = gson.fromJson(json, LoadRequest.class);
        return out;
    }

    public static LoginResult decodeLoginResult(Reader json){
        LoginResult out;
        Gson gson = new Gson();
        out = gson.fromJson(json, LoginResult.class);
        return out;
    }

    public static RegisterResult decodeRegisterResult(Reader json){
        RegisterResult out;
        Gson gson = new Gson();
        out = gson.fromJson(json, RegisterResult.class);
        return out;
    }

    public static PersonGetAllResult decodePersonGetAllResult(Reader json){
        PersonGetAllResult out;
        Gson gson = new Gson();
        out = gson.fromJson(json, PersonGetAllResult.class);
        return out;
    }

    public static EventGetAllResult decodeEventGetAllResult(Reader json){
        EventGetAllResult out;
        Gson gson = new Gson();
        out = gson.fromJson(json, EventGetAllResult.class);
        return out;
    }

    public static PersonIDResult decodePersonIDResult(Reader json){
        PersonIDResult out;
        Gson gson = new Gson();
        out = gson.fromJson(json, PersonIDResult.class);
        return out;
    }


    public static RegisterRequest decodeRegisterRequest(Reader json)  {
        RegisterRequest out;
        Gson gson = new Gson();

        out = gson.fromJson(json, RegisterRequest.class);
        return out;
    }

    public static StringArray decodeNames(String file){
        Gson gson = new Gson();
        try{
            StringArray temp = gson.fromJson(new FileReader(file), StringArray.class);
            return temp;

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public static LocationArray decodeLocations(String file){
        Gson gson = new Gson();
        try{
            LocationArray temp = gson.fromJson(new FileReader(file), LocationArray.class);
            return temp;

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}