package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import ObjectCodeDecode.Decoder;
import Services.LoadService;
import Result.LoadResult;
import Request.LoadRequest;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoadResult myLoadResult = new LoadResult();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                LoadService myLoadService = new LoadService();

                Reader reader = new InputStreamReader(exchange.getRequestBody());
                String readstring = readString(exchange.getRequestBody());
                LoadRequest myLoadRequest = Decoder.decodeLoadRequest(readstring);

                myLoadResult = myLoadService.load(myLoadRequest);
                if (myLoadResult.isSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    String jsonStr = "{\"message\" :\"Successfully added " + myLoadResult.getNumUsers() +
                            " users, " + myLoadResult.getNumPersons() + " persons, and " + myLoadResult.getNumEvents() +
                            " events to the database.\"}";
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);
                    respBody.close();
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    String jsonStr = new String("{\"message\" : \"" + myLoadResult.getMessage() + "\"}");
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

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}