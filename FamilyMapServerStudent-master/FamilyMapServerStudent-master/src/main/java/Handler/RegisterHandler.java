package Handler;

import ObjectCodeDecode.Decoder;
import ObjectCodeDecode.Encoder;
import Services.RegisterService;
import Result.RegisterResult;
import Request.RegisterRequest;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        RegisterResult myResult = new RegisterResult();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                RegisterService myRegisterService = new RegisterService();
                Reader reader = new InputStreamReader(exchange.getRequestBody());
                RegisterRequest myRequest = Decoder.decodeRegisterRequest(reader);
                myResult = myRegisterService.register(myRequest);

                if (myResult.getSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    String jsonStr = Encoder.encode(myResult);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);
                    respBody.close();

                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    String jsonStr = new String("{\"message\" : \"" + myResult.getMessage() + "\"}");
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