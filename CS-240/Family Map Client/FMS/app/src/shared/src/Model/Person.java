package Model;

import java.util.Objects;
import java.util.Random;

public class Person {
    private String personID = "";
    private String associatedUsername = "";
    private String firstName = "";
    private String lastName = "";
    private String gender = "";
    private String fatherID;
    private String motherID;
    private String spouseID;

    public Person(String personID, String associatedUserName, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUserName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        if (fatherID == null || fatherID.equals("")) fatherID = null;
        else this.fatherID = fatherID;
        if (spouseID == null || spouseID.equals("")) spouseID = null;
        else this.spouseID = spouseID;
        if (motherID == null || motherID.equals("")) motherID = null;
        else this.motherID = motherID;
    }

    public Person(User user) {
        associatedUsername = user.getUserName();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        gender = user.getGender();
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 12;
        Random random = new Random();
        personID = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

    }

    public String getFatherID() {
        return fatherID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getPersonID() {
        return personID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personID.equals(person.personID) &&
                associatedUsername.equals(person.associatedUsername) &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                gender.equals(person.gender) &&
                Objects.equals(fatherID, person.fatherID) &&
                Objects.equals(motherID, person.motherID) &&
                Objects.equals(spouseID, person.spouseID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }


    public void setPersonID(String personID) {
        this.personID = personID;
    }


    public void setFatherID(String fatherID) {
        if (!fatherID.equals("")) this.fatherID = fatherID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setMotherID(String motherID) {
        if (!motherID.equals("")) this.motherID = motherID;
    }

    public void setSpouseID(String spouseID) {
        if (!spouseID.equals("")) this.spouseID = spouseID;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }
}
