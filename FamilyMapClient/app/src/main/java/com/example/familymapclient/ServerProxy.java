package com.example.familymapclient;


import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.PersonModel;
import ObjectCodeDecode.Decoder;
import ObjectCodeDecode.Encoder;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.EventGetAllResult;
import Result.LoginResult;
import Result.PersonGetAllResult;
import Result.PersonIDResult;
import Result.RegisterResult;

public class ServerProxy {

    public LoginResult getLoginUrl(URL url, LoginRequest loginRequest) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.addRequestProperty("Accept", "application/json");

            //connect maybe goes here

            String json = Encoder.encode(loginRequest);

            OutputStream reqBody = connection.getOutputStream();
            writeString(json, reqBody);
            reqBody.close();

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(connection.getInputStream());
                LoginResult out = Decoder.decodeLoginResult(reader);
                out.setSuccess(true);
                connection.getInputStream().close();
                return out;
            } else {
                LoginResult out = new LoginResult();
                out.setSuccess(false);
                out.setMessage(connection.getResponseMessage());
                return out;
            }
        } catch (Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
        }
        return null;
    }

    public RegisterResult getRegisterUrl(URL url, RegisterRequest request) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.addRequestProperty("Accept", "application/json");

            String json = Encoder.encode(request);

            OutputStream reqBody = connection.getOutputStream();
            writeString(json, reqBody);
            reqBody.close();

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(connection.getInputStream());
                RegisterResult out = Decoder.decodeRegisterResult(reader);
                out.setSuccess(true);
                connection.getInputStream().close();
                return out;
            } else {
                RegisterResult out = new RegisterResult();
                out.setSuccess(false);
                out.setMessage(connection.getResponseMessage());
                return out;
            }
        } catch (Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
        }
        return null;

    }

    public EventGetAllResult getAllEventsUrl(URL url, String auth) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.addRequestProperty("Authorization", auth);
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(connection.getInputStream());
                EventGetAllResult out = Decoder.decodeEventGetAllResult(reader);
                out.setSuccess(true);
                return out;

            } else {
                EventGetAllResult out = new EventGetAllResult();
                out.setSuccess(false);
                out.setMessage(connection.getResponseMessage());
                return out;
            }

        } catch (Exception e) {
            Log.e("Httpclient", e.getMessage(), e);
            EventGetAllResult badResponse = new EventGetAllResult();
            badResponse.setMessage("Bad Request");
            badResponse.setSuccess(false);
            return badResponse;
        }
    }

    public PersonGetAllResult getAllPeopleUrl(URL url, String auth) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.addRequestProperty("Authorization", auth);
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(connection.getInputStream());
                PersonGetAllResult out = Decoder.decodePersonGetAllResult(reader);
                out.setSuccess(true);

                return out;

            } else {
                PersonGetAllResult out = new PersonGetAllResult();
                out.setSuccess(false);
                out.setMessage(connection.getResponseMessage());
                return out;
            }

        } catch (Exception e) {
            Log.e("Httpclient", e.getMessage(), e);
        }
        return null;
    }

    public PersonIDResult getPersonURL(URL url, String auth) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.addRequestProperty("Authorization", auth);
            connection.addRequestProperty("Accept", "application/json");
            try{
                connection.connect();
            }catch (Exception e){
                throw e;
            }


            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Reader reader = new InputStreamReader(connection.getInputStream());
                PersonIDResult out = Decoder.decodePersonIDResult(reader);
                out.setSuccess(true);
                return out;

            } else {
                PersonIDResult out = new PersonIDResult();
                out.setSuccess(false);
                out.setMessage(connection.getResponseMessage());
                return out;
            }

        } catch (Exception e) {
            Log.e("Httpclient", e.getMessage(), e);
        }
        return null;
    }


    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
