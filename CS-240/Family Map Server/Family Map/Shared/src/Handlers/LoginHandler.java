package Handlers;

import Request.LoginRequest;
import Result.LoginResult;
import Services.Login;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoginResult result = new LoginResult();
        if (exchange.getRequestMethod().toUpperCase().equals("POST")){
            Login loggedIn = new Login();
            Reader reader = new InputStreamReader(exchange.getRequestBody());
            LoginRequest request = new LoginRequest();

            JSONTokener tokener = new JSONTokener(reader);
            JSONObject rootObj = new JSONObject(tokener);
            request.setUserName(rootObj.getString("userName"));
            request.setPassword(rootObj.getString("password"));
            result = loggedIn.login(request);
            if (result.getSuccess()) exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            else exchange.sendResponseHeaders(400, 0);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(result);

            /*StringBuilder str = new StringBuilder("{\n");
            String toString = "";

            if (result.getSuccess()){
                exchange.sendResponseHeaders(200, 0);
                str.append(result.getAuthToken());
                str.append('\n');
                str.append(result.getUserName());
                str.append('\n');
                str.append(result.getPersonID());
                str.append('\n');
                str.append("true");
                str.append("\n}");
            }
            else{
                exchange.sendResponseHeaders(401, 0);
                str.append(result.getMessage());
                str.append('\n');
                str.append("false");
                str.append("\n}");
            }
            toString = str.toString();

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
