package ObjectCodeDecode;

import Request.LoginRequest;
import Request.RegisterRequest;
import Result.*;
import com.google.gson.Gson;

public class Encoder {

    public Encoder(){
        //To be implemented later
    }

    public static String encode(LoginResult result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        return jsonStr;
    }

    public static String encode(RegisterResult result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        return jsonStr;
    }

    public static String encode(ClearResult result){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        return jsonStr;
    }

    public static String encode(PersonIDResult result){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        return jsonStr;
    }

    public static String encode(PersonGetAllResult result){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        return jsonStr;
    }

    public static String encode(EventIDResult result){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        return jsonStr;
    }

    public static String encode(EventGetAllResult result){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        return jsonStr;
    }

    public static String encode(LoginRequest request){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(request);
        return jsonStr;
    }

    public static String encode (RegisterRequest request){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(request);
        return jsonStr;
    }
}
