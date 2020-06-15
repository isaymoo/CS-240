package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
            String requestedURL = exchange.getRequestURI().toString();

            if (requestedURL.length() == 1) {
                String urlPath = new String("web/index.html");
                Path filePath = FileSystems.getDefault().getPath(urlPath);

                exchange.sendResponseHeaders(200, 0);

                Files.copy(filePath, exchange.getResponseBody());

                exchange.getResponseBody().close();

            } else {
                String urlPath = "web" + requestedURL;
                Path filePath = FileSystems.getDefault().getPath(urlPath);


                File file = new File(filePath.toString());
                if (Files.exists(filePath)) {
                    exchange.sendResponseHeaders(200, 0);
                    Files.copy(filePath, exchange.getResponseBody());

                } else {
                    exchange.sendResponseHeaders(404, 0);
                    Files.copy(Paths.get("web/HTML/404.html"), exchange.getResponseBody());
                }


                exchange.getResponseBody().close();

            }
        }
    }
}
