package Handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Result.EventIDResult;
import Result.EventResult;
import Services.EventID;
import Services.EventService;

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        EventResult allResult = new EventResult();
        EventIDResult individualResult = new EventIDResult();
        if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
            String authToken = exchange.getRequestHeaders().getFirst("Authorization");
            StringBuilder url = new StringBuilder(exchange.getRequestURI().toString());
            url.deleteCharAt(0);
            String[] test = url.toString().split("/");
            if (test.length > 2 || test.length < 1) {
                allResult.setSuccess(false);
                allResult.setMessage("not long enough url");
            } else if (test.length == 1) {
                EventService eventService = new EventService();
                allResult = eventService.event(authToken);
                if (allResult.getMessage() == null) {
                    exchange.sendResponseHeaders(200, 0);
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String jsonString = gson.toJson(allResult);
                    /*StringBuilder str = new StringBuilder("{\n");
                    str.append("data:");
                    for (Event event : allResult.getEvents()){
                        str.append("{\n");
                        CreateEvent(str, event.getAssociatedUserName(), event.getEventID(), event.getPersonID(), event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear(), individualResult);
                        str.append("}");
                    }
                    str.append("\"success\": \"true\"\n}");
                    String toString = str.toString();
                     */

                    OutputStream response = exchange.getResponseBody();
                    OutputStreamWriter sw = new OutputStreamWriter(response);
                    sw.write(jsonString);
                    sw.flush();

                    //response.close();
                    exchange.close();
                } else {
                    exchange.sendResponseHeaders(400, 0);
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String jsonString = gson.toJson(allResult);
                    /*StringBuilder str = new StringBuilder("{\n");
                    str.append("\"message\": \"" + allResult.getMessage() + "\"\n");
                    str.append("\"success\": \"false\"\n}");
                    String toString = str.toString();

                     */

                    OutputStream response = exchange.getResponseBody();
                    OutputStreamWriter sw = new OutputStreamWriter(response);
                    sw.write(jsonString);
                    sw.flush();

                    //response.close();
                    exchange.close();
                }
            } else {
                EventID eventID = new EventID();
                StringBuilder id = new StringBuilder(url);
                String ids = id.substring(6);
                individualResult = eventID.eventID(ids, authToken);
                if (individualResult.getSuccess()) {
                    exchange.sendResponseHeaders(200, 0);
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String jsonString = gson.toJson(individualResult);
                    /*
                    StringBuilder str = new StringBuilder("{\n");
                    CreateEvent(str, individualResult.getAssociatedUserName(), individualResult.getEventID(), individualResult.getPersonID(), individualResult.getLatitude(), individualResult.getLongitude(), individualResult.getCountry(), individualResult.getCity(), individualResult.getEventType(), individualResult.getYear(), individualResult);
                    str.append("\"success\": \"true\"\n}");
                    String toString = str.toString();

                     */

                    OutputStream response = exchange.getResponseBody();
                    OutputStreamWriter sw = new OutputStreamWriter(response);
                    sw.write(jsonString);
                    sw.flush();

                    //response.close();
                    exchange.close();
                } else {
                    exchange.sendResponseHeaders(400, 0);
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String jsonString = gson.toJson(individualResult);
                    /*
                    StringBuilder str = new StringBuilder("{\n");
                    str.append("\"message\": \"" + individualResult.getMessage() + "\"\n");
                    str.append("\"success\": \"false\"\n}");
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
    }

    private void CreateEvent(StringBuilder str, String associatedUserName, String eventID2, String personID, float latitude, float longitude, String country, String city, String eventType, int year, EventIDResult individualResult) {
        str.append("\"associatedUsername\": \"" + associatedUserName + "\"\n");
        str.append("\"eventID\": \"" + eventID2 + "\"\n");
        str.append("\"personID\": \"" + personID + "\"\n");
        str.append("\"latitude\": \"" + latitude + "\"\n");
        str.append("\"longitude\": \"" + longitude + "\"\n");
        str.append("\"country\": \"" + country + "\"\n");
        str.append("\"city\": \"" + city + "\"\n");
        str.append("\"eventType\": \"" + eventType + "\"\n");
        str.append("\"year\": \"" + year + "\"\n");
    }
}
