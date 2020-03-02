package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import DataAccessObjects.Database;
import ObjectCodeDecode.Encoder;
import Services.PersonIDService;
import Result.PersonIDResult;
import Services.PersonGetAllService;
import Result.PersonGetAllResult;

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        PersonIDResult myPersonIDResult = new PersonIDResult();
        PersonGetAllResult myPersonGetAllResult = new PersonGetAllResult();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                if(exchange.getRequestHeaders().containsKey("Authorization")){
                    String authToken = exchange.getRequestHeaders().getFirst("Authorization");
                    String requestedURL = exchange.getRequestURI().toString();
                    StringBuilder url = new StringBuilder(requestedURL);
                    url.deleteCharAt(0); //gets rid of the first "/".

                    String[] arguments = url.toString().split("/");
                    if (arguments.length > 2 || arguments.length < 1){
                        myPersonIDResult.setSuccess(false);
                        myPersonIDResult.setMessage("Invalid number of arguments");
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String jsonStr = new String("{\"message\" : \"" + myPersonIDResult.getMessage() + "\"}");
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(jsonStr, respBody);
                        respBody.close();
                    } else if (arguments.length == 2) { //has a personID
                        PersonIDService myIDService = new PersonIDService();
                        myPersonIDResult = myIDService.personID(arguments[1], authToken);

                        if (myPersonIDResult.getSuccess()){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            String jsonStr = Encoder.encode(myPersonIDResult);
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();

                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String jsonStr = new String("{\"message\" : \"" + myPersonIDResult.getMessage() + "\"}");
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        }
                    } else if (arguments.length == 1){
                        PersonGetAllService myPersonGetAllService = new PersonGetAllService();
                        PersonGetAllResult out = myPersonGetAllService.personGetAll(authToken);
                        if (!out.isSuccess()) {
                            throw new Database.DatabaseException(out.getMessage());
                        }
                        myPersonGetAllResult.setData(out.getData());
                        myPersonGetAllResult.setSuccess(out.isSuccess());

                        if (myPersonGetAllResult.isSuccess()){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            String jsonStr = Encoder.encode(myPersonGetAllResult);
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String jsonStr = new String("{\"message\" : \"" + myPersonGetAllResult.getMessage() + "\"}");
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            String jsonStr = new String("{\"message\": \"Internal server error\"}");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();
        }
        catch (Database.DatabaseException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            String jsonStr = new String("{\"message\" : \"" + e.getMessage() + "\"}");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }


}