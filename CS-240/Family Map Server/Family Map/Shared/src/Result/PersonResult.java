package Result;

import Model.Person;

public class PersonResult {
    private Person[] data;
    private String message;
    private boolean success = false;

    public Person[] getPersons() {
        return data;
    }

    public void setPersons(Person[] persons) {
        this.data = persons;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void printAllPersons(){
        for(Person person : data){
            System.out.println("{\n");
            System.out.println("\"associatedUsername\": \"" + person.getAssociatedUsername() + "\"");
            System.out.println("\"personID\": \"" + person.getPersonID() + "\"");
            System.out.println("\"firstName\": \"" + person.getFirstName() + "\"");
            System.out.println("\"lastName\": \"" + person.getLastName() + "\"");
            System.out.println("\"gender\": \"" + person.getGender() + "\"");
            if (person.getFatherID() != "") System.out.println("\"fatherID\": \"" + person.getFatherID() + "\"");
            if (person.getMotherID() != "") System.out.println("\"motherID\": \"" + person.getMotherID() + "\"");
            if (person.getSpouseID() != "") System.out.println("\"spouseID\": \"" + person.getSpouseID() + "\"");
            System.out.println("}");
        }
    }
}
