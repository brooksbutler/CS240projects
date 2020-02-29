package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import ObjectCodeDecode.Encoder;
import Services.ClearService;
import Result.ClearResult;

public class ClearHandler implements HttpHandler {
    public ClearHandler(){
        //To be implemented later
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
