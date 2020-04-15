package com.example.familymapclient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import Request.LoginRequest;
import Request.RegisterRequest;
import Result.EventGetAllResult;
import Result.LoginResult;
import Result.PersonGetAllResult;
import Result.PersonIDResult;
import Result.RegisterResult;

import static junit.framework.Assert.*;

public class ServerProxyTest {

    private URL testURL;
    private ServerProxy proxy;
    private RegisterRequest request;

    @BeforeEach
    public void setUP(){
        proxy = new ServerProxy();
        try{
            testURL = new URL("http://");
        } catch (Exception e ){
            assertEquals("Throwing exception ", e.getMessage());
        }
        request = new RegisterRequest();
        request.setServerPort("8080");
        request.setServerHost("localhost");
        request.setUserName("username");
        request.setPassword("password");
        request.setGender("m");
        request.setEmail("email");
        request.setFirstName("John");
        request.setLastName("Doe");
    }

    @AfterEach
    public void tearDown() throws MalformedURLException {
        testURL = null;
        proxy = null;
        request = null;

        try{
            URL clearURL = new URL("http://localhost:8080/clear/");
            HttpURLConnection connection = (HttpURLConnection) clearURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.connect();
            System.out.println(connection.getResponseCode());
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetRegisterURL(){
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        RegisterResult expectedResult = new RegisterResult();

        expectedResult.setUserName(request.getUserName());
        expectedResult.setSuccess(true);

        RegisterResult out = proxy.getRegisterUrl(testURL, request);
        expectedResult.setAuthToken(out.getAuthToken());
        expectedResult.setPersonId(out.getPersonID());

        assertEquals(expectedResult, out);
    }

    @Test
    public void testGetRegisterURLFail(){
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        RegisterResult expectedResult = new RegisterResult();

        expectedResult.setUserName(request.getUserName());
        expectedResult.setSuccess(true);

        RegisterResult out = proxy.getRegisterUrl(testURL, request);
        expectedResult.setAuthToken(out.getAuthToken());
        expectedResult.setPersonId(out.getPersonID());

        assertEquals(expectedResult, out);

        RegisterResult expectedResponse2 = new RegisterResult();
        expectedResponse2.setSuccess(false);
        expectedResponse2.setMessage("Bad Request");


        out = proxy.getRegisterUrl(testURL, request);
        assertEquals(expectedResponse2, out);
    }

    @Test
    public void testGetLoginURL(){
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        RegisterResult expectedResult = new RegisterResult();

        expectedResult.setUserName(request.getUserName());
        expectedResult.setSuccess(true);

        RegisterResult out = proxy.getRegisterUrl(testURL, request);
        expectedResult.setAuthToken(out.getAuthToken());
        expectedResult.setPersonId(out.getPersonID());

        assertEquals(expectedResult, out);

        LoginRequest requestLogin = new LoginRequest();
        requestLogin.setServerPort("8080");
        requestLogin.setServerHost("localhost");
        requestLogin.setUserName("username");
        requestLogin.setPassword("password");

        try{
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/login");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        LoginResult expectedResponseLogin = new LoginResult();
        expectedResponseLogin.setSuccess(true);
        expectedResponseLogin.setUserName(request.getUserName());

        LoginResult outLogin = proxy.getLoginUrl(testURL, requestLogin);
        expectedResponseLogin.setPersonId(outLogin.getPersonId());
        expectedResponseLogin.setAuthToken(outLogin.getAuthToken());

        assertEquals(expectedResponseLogin, outLogin);
    }

    @Test
    public void testGetLoginURLFail(){
        LoginRequest requestLogin = new LoginRequest();
        requestLogin.setServerPort("8080");
        requestLogin.setServerHost("localhost");
        requestLogin.setUserName("Bill");
        requestLogin.setPassword("password");

        try{
            testURL = new URL("http://" + requestLogin.getServerHost() + ":" + requestLogin.getServerPort() + "/user/login");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        LoginResult expectedResponseLogin = new LoginResult();
        expectedResponseLogin.setSuccess(false);
        expectedResponseLogin.setMessage("Bad Request");

        LoginResult outLogin = proxy.getLoginUrl(testURL, requestLogin);

        assertEquals(expectedResponseLogin, outLogin);
    }

    @Test
    public void testEventGetAllURL(){
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        RegisterResult out = proxy.getRegisterUrl(testURL, request);

        String authToken = out.getAuthToken();
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/event/");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        EventGetAllResult outTwo = proxy.getAllEventsUrl(testURL, authToken);

        assertEquals(outTwo.getData().length, 91);
        assertTrue(outTwo.isSuccess());
    }

    @Test
    public void testEventGetAllURLFail(){
        String authToken = "BogusAuthToken";
        try {
            testURL = new URL("http://localhost:8080/event/");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        EventGetAllResult outTwo = proxy.getAllEventsUrl(testURL, authToken);

        assertEquals(outTwo.getMessage(), "Bad Request");
        assertFalse(outTwo.isSuccess());
    }

    @Test
    public void testPersonGetAllURL(){
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        RegisterResult out = proxy.getRegisterUrl(testURL, request);

        String authToken = out.getAuthToken();
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/person/");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        PersonGetAllResult outTwo = proxy.getAllPeopleUrl(testURL, authToken);

        assertEquals(outTwo.getData().length, 31);
        assertTrue(outTwo.isSuccess());
    }

    @Test
    public void testPersonGetALLURLFail(){
        String authToken = "Bogus";
        try {
            testURL = new URL("http://localhost:8080/person/");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }


        PersonGetAllResult outTwo = proxy.getAllPeopleUrl(testURL, authToken);

        assertEquals(outTwo.getMessage(), "Bad Request");
        assertFalse(outTwo.isSuccess());
    }

    @Test
    public void testGetPersonURL(){
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        RegisterResult out = proxy.getRegisterUrl(testURL, request);

        String authToken = out.getAuthToken();
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/person/" + out.getPersonID());
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        PersonIDResult outTwo = proxy.getPersonURL(testURL, authToken);

        assertEquals(outTwo.getFirstName(), "John");
        assertEquals(outTwo.getLastName(), "Doe");
        assertTrue(outTwo.getSuccess());
    }

    @Test
    public void testGetPersonURLFail(){
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        RegisterResult out = proxy.getRegisterUrl(testURL, request);
        String authToken = out.getAuthToken();

        RegisterRequest requestTwo = new RegisterRequest();
        requestTwo.setServerPort("8080");
        requestTwo.setServerHost("localhost");
        requestTwo.setUserName("userTwo");
        requestTwo.setPassword("password");
        requestTwo.setGender("m");
        requestTwo.setEmail("email");
        requestTwo.setFirstName("Jack");
        requestTwo.setLastName("Doe");

        RegisterResult out2 = proxy.getRegisterUrl(testURL, requestTwo);

        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/person/" + out2.getPersonID());
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        PersonIDResult outTwo = proxy.getPersonURL(testURL, authToken);

        assertEquals(outTwo.getMessage(), "Bad Request");
        assertFalse(outTwo.getSuccess());
    }

}