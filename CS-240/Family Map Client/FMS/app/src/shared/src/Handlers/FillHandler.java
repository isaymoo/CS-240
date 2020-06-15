package Handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Result.FillResult;
import Services.Fill;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
            Fill fillService = new Fill();
            StringBuilder url = new StringBuilder(exchange.getRequestURI().toString());
            url.deleteCharAt(0);
            FillResult result = new FillResult();
            String[] test = url.toString().split("/");
            if (test.length <= 1 || test.length > 3) {
                result.setSuccess(false);
                result.setMessage("error not long enough url");
            } else {
                String userName = test[1];
                int numGenerations = 4;
                result.setSuccess(true);
                if (test.length == 3) {
                    numGenerations = Integer.parseInt(test[2]);
                }
                result = fillService.fill(userName, numGenerations);
            }

            exchange.sendResponseHeaders(200, 0);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(result);
            /*
            StringBuilder str = new StringBuilder("{\n");
            str.append(result.getMessage());
            str.append('\n');
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

            success = true;

        }
        if (success != true) {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
        }
    }
}
