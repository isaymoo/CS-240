package Handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import Result.ClearResult;
import Services.Clear;

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
            Clear clearService = new Clear();
            ClearResult result = clearService.clear();

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(result);
            /*
            StringBuilder str = new StringBuilder("{\n");
            str.append("\"message\": \"" + result.getMessage());
            str.append("\",\n\"success\": ");
            if (result.getSuccess()) str.append("true");
            else str.append("false");
            str.append("\n}");
            String toString = str.toString();

             */

            OutputStream response = exchange.getResponseBody();
            OutputStreamWriter sw = new OutputStreamWriter(response);
            sw.write(jsonString);
            sw.flush();

            //response.close();
            exchange.close();


        }
    }
}
