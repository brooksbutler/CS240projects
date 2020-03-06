package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.*;

public class DefaultFileHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                String requestedURL = exchange.getRequestURI().toString();

                File file = new File("web"+requestedURL);
                if(file.exists()){
                    if (requestedURL.length() == 1){
                        //return index.html
                        String urlPath = new String("web/index.html" );
                        Path filePath = FileSystems.getDefault().getPath(urlPath);

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                        Files.copy(filePath, exchange.getResponseBody());
                        exchange.getResponseBody().close();
                    } else {
                        String urlPath = "web" + requestedURL;
                        Path filePath = FileSystems.getDefault().getPath(urlPath);

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        Files.copy(filePath, exchange.getResponseBody());
                        exchange.getResponseBody().close();

                    }
                    success = true;
                }
                else {
                    String urlPath = "web/HTML/404.html";
                    Path filePath = FileSystems.getDefault().getPath(urlPath);

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    Files.copy(filePath, exchange.getResponseBody());
                    exchange.getResponseBody().close();

                }

                //get url string (/index.html /main.css)
                //create file ^, check if exists
                //if not exists: file = new file("web...404
                //send headers
                //copy
                //close3



//                File file = new File("");
//                if(file.exists())

            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    public static boolean isPathValid(String path) {
        try {

            Paths.get(path);

        } catch (InvalidPathException ex) {
            return false;
        }

        return true;
    }
}




