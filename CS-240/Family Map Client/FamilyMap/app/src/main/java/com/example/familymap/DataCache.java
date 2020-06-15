package com.example.familymap;

import java.util.HashMap;
import java.util.Map;

import Model.Event;
import Model.Person;

public class DataCache {
    private static DataCache cache;

    private Person person;
    private String authToken;
    private boolean loggedIn = false;
    private Map<String, Person> allPersons;
    private Map<String, Event> allEvents;

    private DataCache(){
        person = new Person("", "", "", "", "", "", "", "");
        authToken = new String();
        loggedIn = false;
        allPersons = new HashMap<>();
        allEvents = new HashMap<>();
    }


    public static DataCache getCache() {
        if (cache == null) cache = new DataCache();
        return cache;
    }

    public static void setCache(DataCache cache) {
        DataCache.cache = cache;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public boolean getsLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Map<String, Person> getAllPersons() {
        return allPersons;
    }

    public void setAllPersons(Person[] persons) {
        for (int i = 0; i < persons.length; i++){
            allPersons.put(persons[i].getPersonID(), persons[i]);
        }
    }

    public Map<String, Event> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(Event[] events) {
        for (int i = 0; i < events.length; i++){
            allEvents.put(events[i].getPersonID(), events[i]);
        }
    }
}
