package main.test;
import main.model.methods.*;
import main.util.AuthToken;
import org.junit.Test;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.*;
import static main.util.ErrorCodes.NOT_AUTHORIZED_ERROR;
import static main.util.Methods.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class AdministrateUser extends BaseTest{

    private String authToken = AuthToken.getAdminLoginToken(client);

    @Test
    public void getBalance(){
        String result = client.processRequest(getBalance, new GetBalance(authToken, donaldAccount.getiBAN()));
        assertThat(result, hasJsonPath("result"));
        assertThat(result, hasJsonPath("result.balance"));

        result = client.processRequest(getBalance, new GetBalance(authToken, dagobertAccount.getiBAN()));
        assertThat(result, hasJsonPath("result"));
        assertThat(result, hasJsonPath("result.balance"));
    }

    @Test
    public void getTransaction(){
        String result = client.processRequest(getTransactionsOverview, new GetTransactionsOverview(authToken, donaldAccount.getiBAN(),1));
        assertThat(result, hasJsonPath("result.length()", equalTo(0)));
        assertThat(result, hasNoJsonPath("error"));

        result = client.processRequest(getTransactionsOverview, new GetTransactionsOverview(authToken, dagobertAccount.getiBAN(), 1));
        assertThat(result, hasJsonPath("result.length()", equalTo(0)));
        assertThat(result, hasNoJsonPath("error"));
    }

    @Test
    public void getBankAccountAccess(){
        String result = client.processRequest(getBankAccountAccess, new GetBankAccountAccess(authToken, donaldAccount.getiBAN()));
        assertThat(result, hasJsonPath("result"));
        assertThat(result, hasJsonPath("result.length()", equalTo(1)));

        result = client.processRequest(getBankAccountAccess, new GetBankAccountAccess(authToken, dagobertAccount.getiBAN()));
        assertThat(result, hasJsonPath("result"));
        assertThat(result, hasJsonPath("result.length()", equalTo(1)));
    }

    @Test
    public void invalidAccess(){
        String auth = AuthToken.getAuthToken(client, "donald", "donald");
        String result = client.processRequest(simulateTime, new SimulateTime(1, auth));
        assertThat(result, hasNoJsonPath("result"));
        assertThat(result, hasJsonPath("error"));
        assertThat(result, hasJsonPath("error.code", equalTo(NOT_AUTHORIZED_ERROR)));

        result = client.processRequest(reset, new Reset(auth));
        assertThat(result, hasNoJsonPath("result"));
        assertThat(result, hasJsonPath("error"));
        assertThat(result, hasJsonPath("error.code", equalTo(NOT_AUTHORIZED_ERROR)));
    }



}
