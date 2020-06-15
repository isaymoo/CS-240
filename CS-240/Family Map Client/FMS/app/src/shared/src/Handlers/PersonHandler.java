package Handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import Result.PersonIDResult;
import Result.PersonResult;
import Services.PersonID;
import Services.PersonService;

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        PersonResult allResult = new PersonResult();
        PersonIDResult individualResult = new PersonIDResult();

        if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
            String authToken = exchange.getRequestHeaders().getFirst("Authorization");
            StringBuilder url = new StringBuilder(exchange.getRequestURI().toString());
            url.deleteCharAt(0);
            String[] test = url.toString().split("/");
            if (test.length > 2 || test.length < 1) {
                allResult.setSuccess(false);
                allResult.setMessage("not long enough url");
            } else if (test.length == 1) {
                PersonService personService = new PersonService();
                allResult = personService.person(authToken);
                if (allResult.getSuccess()) {
                    exchange.sendResponseHeaders(200, 0);
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(allResult);
                    /*
                    StringBuilder str = new StringBuilder("{\n");
                    str.append("data:");
                    for (Person person : allResult.getPersons()){
                        str.append("{\n");
                        CreatePerson(str, person.getAssociatedUserName(), person.getPersonID(), person.getFirstName(), person.getLastName(), person.getGender(), person.getFatherID(), person.getMotherID(), person.getSpouseID(), individualResult);
                        str.append("}\n");
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
                    /*
                    StringBuilder str = new StringBuilder("{\n");
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
                PersonID personID = new PersonID();
                StringBuilder id = new StringBuilder(url);
                String ids = id.substring(7);
                individualResult = personID.personID(ids, authToken);
                if (individualResult.getSuccess()) {
                    exchange.sendResponseHeaders(200, 0);
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String jsonString = gson.toJson(individualResult);
                    /*
                    StringBuilder str = new StringBuilder("{\n");
                    CreatePerson(str, individualResult.getAssociatedUserName(), individualResult.getPersonID(), individualResult.getFirstName(), individualResult.getLastName(), individualResult.getGender(), individualResult.getFatherID(), individualResult.getMotherID(), individualResult.getSpouseID(), individualResult);
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

    private void CreatePerson(StringBuilder str, String associatedUserName, String personID2, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID, PersonIDResult individualResult) {
        str.append("\"associatedUsername\": \"" + associatedUserName + "\"\n");
        str.append("\"personID\": \"" + personID2 + "\"\n");
        str.append("\"firstName\": \"" + firstName + "\"\n");
        str.append("\"lastName\": \"" + lastName + "\"\n");
        str.append("\"gender\": \"" + gender + "\"\n");
        if (fatherID != "") str.append("\"fatherID\": \"" + fatherID + "\"\n");
        if (motherID != "") str.append("\"motherID\": \"" + motherID + "\"\n");
        if (spouseID != "") str.append("\"spouseID\": \"" + spouseID + "\"\n");
    }
}
