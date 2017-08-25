package main.model.methods;

import java.math.BigDecimal;

public class SetValue {

    private String authToken;
    private String key;
    private BigDecimal value;
    private String date;

    public SetValue(String authToken, String key, double value, String date) {
        this.authToken = authToken;
        this.key = key;
        this.value = new BigDecimal(value);
        this.date = date;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
