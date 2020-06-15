package Model;

import java.util.Random;

public class AuthToken {
    private User associatedUser;
    private String associatedUserID = "";
    private String authToken = "";

    public AuthToken(String associatedUserID, String authToken) {
        this.associatedUserID = associatedUserID;
        this.authToken = authToken;
    }

    public AuthToken(User u) {
        this.associatedUserID = u.getUserName();
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 16;
        Random random = new Random();
        authToken = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public User getAssociatedUser() {
        return associatedUser;
    }

    public String getAssociatedUserID() {
        return associatedUserID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAssociatedUser(User associatedUser) {
        this.associatedUser = associatedUser;
    }

    public void setAssociatedUserID(String associatedUserID) {
        this.associatedUserID = associatedUserID;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
