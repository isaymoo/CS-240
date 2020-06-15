package Services;

import DataAccess.Database;
import Result.FillResult;

public class Fill {
    private Database db;

    public Fill() {
        db = new Database();
    }

    public FillResult fill(String userName, int numGenerations) {
        FillResult result = new FillResult();
        try {

            db.openConnection();
            if (numGenerations < 0) throw new DataAccessException("error invalid generation");
            EventDao myEventDao = db.getMyEventDao();
            PersonDao myPersonDao = db.getMyPersonDao();
            UserDao myUserDao = db.getMyUserDao();
            try {
                User user = myUserDao.find(userName);
                if (user == null) throw new DataAccessException("error user not found");
                myPersonDao.removeAllOfUser(userName);
                myEventDao.removeAllOfUser(userName);
                Person userPerson = new Person(user);
                user.setPersonID(userPerson.getPersonID());

                boolean tryAgain = false;
                Event birth = new Event(userPerson);
                do {
                    try {
                        birth = new Event(userPerson);
                        myEventDao.insert(birth);
                    } catch (DataAccessException e) {
                        tryAgain = true;
                    }
                } while (tryAgain == true);
                myPersonDao.addGenerations(userPerson, numGenerations, myEventDao, 1995);
                myPersonDao.insert(userPerson);
                int numPersonsAdded = (int) Math.pow(2, (numGenerations + 1));
                numPersonsAdded--;
                result.setMessage("Successfully added " + numPersonsAdded + " persons and " + ((numPersonsAdded * 3) + 1) + " events to the database");
                result.setSuccess(true);
                db.closeConnection(true);
            } catch (DataAccessException e) {
                result.setMessage(e.getMessage());
                result.setSuccess(false);
                try {
                    db.closeConnection(false);
                } catch (DataAccessException dataAccessException) {
                    result.setMessage(dataAccessException.getMessage());
                }
            }

        } catch (DataAccessException e) {
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            try {
                db.closeConnection(false);
            } catch (DataAccessException dataAccessException) {
                result.setMessage(dataAccessException.getMessage());
            }
        }

        return result;
    }

}
