package main.model.methods;

public class CloseAccount {

    private String authToken;
    private String iBAN;

    public CloseAccount(String authToken, String iBAN) {
        this.authToken = authToken;
        this.iBAN = iBAN;
    }
}
