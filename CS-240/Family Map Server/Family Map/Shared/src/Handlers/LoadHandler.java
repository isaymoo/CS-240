package Handlers;

import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;
import Services.Load;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoadResult result = new LoadResult();
        if (exchange.getRequestMethod().toUpperCase().equals("POST")){
            Load load = new Load();
            LoadRequest request = new LoadRequest();
            Reader reader = new InputStreamReader(exchange.getRequestBody());



            JSONTokener tokener = new JSONTokener(reader);
            JSONObject rootObj = new JSONObject(tokener);
            JSONArray users = rootObj.getJSONArray("users");
            JSONArray persons = rootObj.getJSONArray("persons");
            JSONArray events = rootObj.getJSONArray("events");

            Event[] eventArray = new Event[events.length()];
            User[] userArray = new User[users.length()];
            Person[] personArray = new Person[persons.length()];

            for (int i = 0; i < users.length(); i++){
                JSONObject user = users.getJSONObject(i);

                String userName = user.getString("userName");
                String password = user.getString("password");
                String email = user.getString("email");
                String firstName = user.getString("firstName");
                String lastName = user.getString("lastName");
                String gender = user.getString("gender");
                String personID = user.getString("personID");

                userArray[i] = new User(personID, userName, password, email, firstName, lastName, gender);
            }

            for (int i = 0; i < persons.length(); i++){
                JSONObject person = persons.getJSONObject(i);
                String firstName = person.getString("firstName");
                String lastName = person.getString("lastName");
                String gender = person.getString("gender");
                String personID = person.getString("personID");
                String fatherID = "";
                if(person.has("fatherID")) fatherID = person.getString("fatherID");
                String motherID = "";
                if(person.has("motherID")) motherID = person.getString("motherID");
                String spouseID = "";
                if(person.has("spouseID")) spouseID = person.getString("spouseID");
                String associatedUsername = person.getString("associatedUsername");

                personArray[i] = new Person(personID, associatedUsername, firstName, lastName, gender, fatherID,
                        motherID, spouseID);
            }

            for (int i = 0; i < events.length(); i++){
                JSONObject event = events.getJSONObject(i);

                String eventID = event.getString("eventID");
                String associatedUsername = event.getString("associatedUsername");
                String personID = event.getString("personID");
                float latitude = (float) event.getDouble("latitude");
                float longitude = (float) event.getDouble("longitude");
                String country = event.getString("country");
                String city = event.getString("city");
                String eventType = event.getString("eventType");
                int year = event.getInt("year");

                eventArray[i] = new Event(eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year);
            }


            request.setEvents(eventArray);
            request.setPersons(personArray);
            request.setUsers(userArray);


            result = load.load(request);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(result);
            /*StringBuilder str = new StringBuilder("{\n");

            if (result.getSuccess()){
                exchange.sendResponseHeaders(200, 0);
                str.append("\"message: \"Successfully added " + result.getNumUsers() + " users, " +
                        result.getNumPersons() + " persons, and " + result.getNumEvents() + " events to the database.\"\n");
                str.append("\"success\": \"true\"\n}");
            }
            else{
                exchange.sendResponseHeaders(401, 0);
                str.append("\"message\": \"" + result.getMessage() + "\"\n");
                str.append("\"success\": \"false\"\n}");
            }
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
