package main.model.methods;

public class CloseAccount {

    private String authToken;
    private String iBAN;

    public CloseAccount(String authToken, String iBAN) {
        this.authToken = authToken;
        this.iBAN = iBAN;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getiBAN() {
        return iBAN;
    }

    public void setiBAN(String iBAN) {
        this.iBAN = iBAN;
    }
}
