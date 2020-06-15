package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection conn;
    private UserDao myUserDao;
    private PersonDao myPersonDao;
    private EventDao myEventDao;
    private AuthTokenDao myAuthTokenDao;

    public Database(){

    }

    //Whenever we want to make a change to our database we will have to open a connection and use
    //Statements created by that connection to initiate transactions
    public Connection openConnection() throws DataAccessException {
        try {
            //The Structure for this Connection is driver:language:path
            //The path assumes you start in the root of your project unless given a non-relative path
            final String CONNECTION_URL = "jdbc:sqlite:familymap.sqlite";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
            myUserDao = new UserDao(conn);
            myPersonDao = new PersonDao(conn);
            myEventDao = new EventDao(conn);
            myAuthTokenDao = new AuthTokenDao(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    public Connection getConnection() throws DataAccessException {
        if(conn == null) {
            return openConnection();
        } else {
            return conn;
        }
    }

    //When we are done manipulating the database it is important to close the connection. This will
    //End the transaction and allow us to either commit our changes to the database or rollback any
    //changes that were made before we encountered a potential error.

    //IMPORTANT: IF YOU FAIL TO CLOSE A CONNECTION AND TRY TO REOPEN THE DATABASE THIS WILL CAUSE THE
    //DATABASE TO LOCK. YOUR CODE MUST ALWAYS INCLUDE A CLOSURE OF THE DATABASE NO MATTER WHAT ERRORS
    //OR PROBLEMS YOU ENCOUNTER
    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                //This will commit the changes to the database
                conn.commit();
            } else {
                //If we find out something went wrong, pass a false into closeConnection and this
                //will rollback any changes we made during this connection
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    public void clearTables() throws DataAccessException
    {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM user");
                stmt.executeUpdate("DELETE FROM person");
                stmt.executeUpdate("DELETE FROM authToken");
                stmt.executeUpdate("DELETE FROM event");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("clear table failed");
        }
        /*myUserDao.clearAll();
        myAuthTokenDao.clearAll();
        myEventDao.clearAll();
        myPersonDao.clearAll();

         */
    }
    public UserDao getMyUserDao(){
        return myUserDao;
    }
    public PersonDao getMyPersonDao(){
        return myPersonDao;
    }
    public EventDao getMyEventDao(){
        return myEventDao;
    }
    public AuthTokenDao getMyAuthTokenDao(){
        return myAuthTokenDao;
    }
}

