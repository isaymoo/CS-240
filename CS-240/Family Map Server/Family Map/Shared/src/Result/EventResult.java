package Result;

import Model.Event;

public class EventResult {
    private Event[] data;
    private String message;
    private boolean success = false;

    public Event[] getEvents() {
        return data;
    }

    public void setEvents(Event[] events) {
        this.data = events;
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



    public void printAllEvents(){
        for (Event event : data){
            System.out.println("{");
            System.out.println("\"associatedUserName\": \"" + event.getAssociatedUsername());
            System.out.println("\"eventID\": \"" + event.getEventID());
            System.out.println("\"personID\": \"" + event.getPersonID());
            System.out.println("\"latitude\": \"" + event.getLatitude());
            System.out.println("\"longitude\": \"" + event.getLongitude());
            System.out.println("\"country\": \"" + event.getCountry());
            System.out.println("\"city\": \"" + event.getCity());
            System.out.println("\"eventType\": \"" + event.getEventType());
            System.out.println("\"year\": \"" + event.getYear());
            System.out.println("}");



        }
    }
}
