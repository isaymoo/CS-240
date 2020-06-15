package DataAccess;

import java.util.ArrayList;
import java.util.Random;

import Model.Event;
import Model.Person;
import Result.ClearResult;

public class PersonDao {
    private final Connection conn;
    private int numEntries = 0;

    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    public void insert(Person personName) throws DataAccessException {
        String sql = "INSERT INTO person (personID, associatedUserName, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (!personName.getGender().equals("m")) {
                if (!personName.getGender().equals("f")) throw new SQLException();
            }
            if (personName.getPersonID() == "" || personName.getAssociatedUsername() == "" || personName.getFirstName() == "" ||
                    personName.getLastName() == "") throw new SQLException();
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, personName.getPersonID());
            stmt.setString(2, personName.getAssociatedUsername());
            stmt.setString(3, personName.getFirstName());
            stmt.setString(4, personName.getLastName());
            stmt.setString(5, personName.getGender());
            stmt.setString(6, personName.getFatherID());
            stmt.setString(7, personName.getMotherID());
            stmt.setString(8, personName.getSpouseID());

            stmt.executeUpdate();
            numEntries++;
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUserName"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }

    public void remove(String personID) throws DataAccessException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("DELETE FROM person WHERE personID = '" + personID + "'");
                numEntries--;
            } finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("delete person failed");
        }
    }

    public ClearResult clearAll() throws DataAccessException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("DELETE FROM person");
                numEntries = 0;
            } finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("clear table failed");
        }
        return null;
    }

    public int getNumEntries() {
        return numEntries;
    }

    public void addGenerations(Person person, int numGenerations, EventDao myEventDao, int personBirthYear) throws DataAccessException {
        Person mother = createMother(person, personBirthYear - 25, myEventDao);
        Person father = createFather(person, personBirthYear - 25, myEventDao);
        mother.setSpouseID(father.getPersonID());
        father.setSpouseID(mother.getPersonID());
        person.setMotherID(mother.getPersonID());
        person.setFatherID(father.getPersonID());


        numGenerations--;

        if (numGenerations > 0) {
            addGenerations(mother, numGenerations, myEventDao, personBirthYear - 25);
            addGenerations(father, numGenerations, myEventDao, personBirthYear - 25);
        }
        this.insert(mother);
        this.insert(father);

    }

    public Person createMother(Person person, int personBirthYear, EventDao myEventDao) {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 12;
        Random random = new Random();
        String ID = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        Person mother = new Person(ID, person.getAssociatedUsername(), "firstName", "lastName", "f", "", "", "");
        Random random1 = new Random();
        String ID1 = random1.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        Event birth = new Event(ID1, mother.getAssociatedUsername(), mother.getPersonID(), (float) 0.4981, (float) 44.287, "country", "city", "birth", personBirthYear);
        Random random2 = new Random();
        String ID2 = random2.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        Event marriage = new Event(ID2, mother.getAssociatedUsername(), mother.getPersonID(), (float) 0.4981, (float) 44.287, "country", "city", "marriage", personBirthYear + 20);
        Random random3 = new Random();
        String ID3 = random3.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        Event death = new Event(ID3, mother.getAssociatedUsername(), mother.getPersonID(), (float) 0.4981, (float) 44.287, "country", "city", "death", personBirthYear + 80);
        try {
            myEventDao.insert(birth);
            myEventDao.insert(marriage);
            myEventDao.insert(death);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return mother;
    }

    public Person createFather(Person person, int personBirthYear, EventDao myEventDao) {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 12;
        Random random = new Random();
        String ID = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        Person father = new Person(ID, person.getAssociatedUsername(), "firstName", "lastName", "m", "", "", "");
        Random random1 = new Random();
        String ID1 = random1.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        Event birth = new Event(ID1, father.getAssociatedUsername(), father.getPersonID(), (float) 0.4981, (float) 44.287, "country", "city", "birth", personBirthYear);
        Random random2 = new Random();
        String ID2 = random2.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        Event marriage = new Event(ID2, father.getAssociatedUsername(), father.getPersonID(), (float) 0.4981, (float) 44.287, "country", "city", "marriage", personBirthYear + 20);
        Random random3 = new Random();
        String ID3 = random3.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        Event death = new Event(ID3, father.getAssociatedUsername(), father.getPersonID(), (float) 0.4981, (float) 44.287, "country", "city", "death", personBirthYear + 80);
        try {
            myEventDao.insert(birth);
            myEventDao.insert(marriage);
            myEventDao.insert(death);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return father;
    }

    public void removeAllOfUser(String userName) {
        Statement stmt = null;
        try {
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM person WHERE associatedUserName = '" + userName + "'");
            } finally {
                if (stmt != null) stmt.close();
                stmt = null;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Person[] returnAllOfUser(String userName) {
        ArrayList<Person> persons = new ArrayList<Person>();
        int numPersons = 0;

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM person WHERE associatedUserName = '" + userName + "'";
            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();
            while (rs.next()) {
                Person person = new Person(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));

                persons.add(person);

                numPersons++;

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        Person[] returnValue = new Person[persons.size()];
        returnValue = persons.toArray(returnValue);

        return returnValue;
    }

}
