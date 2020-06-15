package Model;

import Request.RegisterRequest;

import java.util.Objects;

public class User {

    private String personID = "";
    private String userName = "";
    private String password = "";
    private String email = "";
    private String firstName = "";
    private String lastName = "";
    private String gender = "";

    public User(String personID, String userName, String password, String email, String firstName, String lastName, String gender) {
        this.personID = personID;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }
    public User(RegisterRequest r){
        userName = r.getUserName();
        password = r.getPassword();
        email = r.getEmail();
        firstName = r.getFirstName();
        lastName = r.getLastName();
        gender = r.getGender();
        personID = "temp";
    }


    public String getPersonID() {
        return personID;
    }

    public String getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return personID.equals(user.personID) &&
                userName.equals(user.userName) &&
                password.equals(user.password) &&
                email.equals(user.email) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                gender.equals(user.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, userName, password, email, firstName, lastName, gender);
    }
}
