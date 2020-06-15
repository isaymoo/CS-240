package Services;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;

public class Load {
    private Database db;
    public Load(){
        db = new Database();
    }
    public LoadResult load(LoadRequest request){
        LoadResult result = new LoadResult();
        try {
            db.openConnection();
            db.clearTables();

            UserDao myUserDao = db.getMyUserDao();
            PersonDao myPersonDao = db.getMyPersonDao();
            EventDao myEventDao = db.getMyEventDao();

            User[] users = request.getUsers();
            Person[] persons = request.getPersons();
            Event[] events = request.getEvents();
            if (persons.length == 0 || users.length == 0 || events.length == 0) throw new DataAccessException("Error missing values");
            for(User user : users){
                myUserDao.insert(user);
            }
            for(Person person : persons){
                myPersonDao.insert(person);
            }
            for(Event event : events){
                myEventDao.insert(event);
            }
            db.closeConnection(true);
            result.setMessage("Successfully added " + users.length + " users, " + persons.length + " persons, and "+ events.length + " events to the database.");
            result.setSuccess(true);
            /*result.setNumUsers(users.length);
            result.setNumPersons(persons.length);
            result.setNumEvents(events.length);

             */

        } catch (DataAccessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            try {
                db.closeConnection(false);
            } catch (DataAccessException dataAccessException) {
                result.setMessage(dataAccessException.getMessage());
            }
        }


        return result;
    }
}
