package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import Services.FillService;
import Result.FillResult;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        FillResult myFillResult = new FillResult();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                FillService myFillService = new FillService();
                String requestedURL = exchange.getRequestURI().toString();
                StringBuilder url = new StringBuilder(requestedURL);
                url.deleteCharAt(0); //gets rid of the first "/".

                String[] arguments = url.toString().split("/");

                if (arguments.length <= 1 || arguments.length > 3){
                    myFillResult.setSuccess(false);
                    myFillResult.setMessage("Invalid number of arguments");
                } else {
                    String userName = arguments[1];
                    int numGenerations = -1;
                    myFillResult.setSuccess(true);

                    if (arguments.length == 3){
                        try{
                            numGenerations = Integer.parseInt(arguments[2]);
                            myFillResult.setSuccess(true);
                        }
                        catch(NumberFormatException num){
                            myFillResult.setMessage("invalid number of generations");
                            myFillResult.setSuccess(false);
                        }
                    }

                    if (myFillResult.isSuccess()){
                        myFillResult = myFillService.fill(userName, numGenerations);
                    }
                }



                if (myFillResult.isSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    String jsonStr = "{\"message\" :\"Successfully added " + myFillResult.getNumPersons() +
                            " persons and " + myFillResult.getNumEvents() + " events to the database.\"}";

                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);

                    respBody.close();
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    String jsonStr = new String("{\"message\" : \"" + myFillResult.getMessage() + "\"}");
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);
                    respBody.close();
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
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}