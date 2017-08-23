package main.test;

import com.jayway.jsonpath.JsonPath;
import main.client.IClient;
import main.model.BankAccount;
import main.model.methods.GetDate;
import main.model.methods.OpenAccount;
import main.model.methods.Reset;
import main.model.methods.SimulateTime;
import main.util.AuthToken;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;

import java.util.Calendar;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasNoJsonPath;
import static main.util.CalendarUtil.getCalenderOfString;
import static main.util.CalendarUtil.getDaysTillNextFirstOfMonth;
import static main.util.Checker.checkSuccess;
import static main.util.Methods.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@Ignore
public class BaseTest {

    public static IClient client = TestSuite.client;

    public static BankAccount donaldAccount;
    public static BankAccount dagobertAccount;
    public static BankAccount daisyAccount;

    public String donaldAuth = AuthToken.getAuthToken(client, "donald", "donald");
    public String dagobertAuth = AuthToken.getAuthToken(client, "dagobert", "dagobert");
    public String daisyAuth = AuthToken.getAuthToken(client, "daisy", "daisy");
    public String adminAuth = AuthToken.getAdminLoginToken(client);

    @BeforeClass
    public static void setUp(){
        String authToken = AuthToken.getAdminLoginToken(client);
        String result = client.processRequest(simulateTime, new SimulateTime(1, authToken));
        assertThat(result, hasJsonPath("result.length()", equalTo(0)));
        addDefaultUsers();
    }

    @AfterClass
    public static void reset(){
        String authToken = AuthToken.getAdminLoginToken(client);
        String result = client.processRequest(reset, new Reset(authToken));
        assertThat(result, hasJsonPath("result.length()", equalTo(0)));
    }

    public static void addDefaultUsers(){
        OpenAccount openAccountObject = new OpenAccount("Donald", "Duck", "D.", "1934-6-9", "123456798", "1313 Webfoot Walk, Duckburg", "+316 12345678", "donald@gmail.com", "donald", "donald");
        String result = client.processRequest(openAccount, openAccountObject);
        assertThat(result, hasJsonPath("result"));
        assertThat(result, hasJsonPath("result.iBAN"));
        assertThat(result, hasJsonPath("result.pinCard"));
        assertThat(result, hasJsonPath("result.pinCode"));
        donaldAccount = new BankAccount();
        donaldAccount.setiBAN((String) JsonPath.read(result, "result.iBAN"));
        donaldAccount.setPinCard((String) JsonPath.read(result, "result.pinCard"));
        donaldAccount.setPinCode((String) JsonPath.read(result, "result.pinCode"));

        openAccountObject.setName("Dagobert");
        openAccountObject.setSsn("123456798");
        openAccountObject.setEmail("dagobert@gamil.com");
        openAccountObject.setUsername("dagobert");
        openAccountObject.setPassword("dagobert");

        result = client.processRequest(openAccount, openAccountObject);
        assertThat(result, hasJsonPath("result"));
        assertThat(result, hasJsonPath("result.iBAN"));
        assertThat(result, hasJsonPath("result.pinCard"));
        assertThat(result, hasJsonPath("result.pinCode"));

        dagobertAccount = new BankAccount();
        dagobertAccount.setiBAN((String) JsonPath.read(result, "result.iBAN"));
        dagobertAccount.setPinCard((String) JsonPath.read(result, "result.pinCard"));
        dagobertAccount.setPinCode((String) JsonPath.read(result, "result.pinCode"));

        openAccountObject.setName("Daisy");
        openAccountObject.setSsn("123456799");
        openAccountObject.setEmail("daisy@gamil.com");
        openAccountObject.setUsername("daisy");
        openAccountObject.setPassword("daisy");

        result = client.processRequest(openAccount, openAccountObject);
        assertThat(result, hasJsonPath("result"));
        assertThat(result, hasJsonPath("result.iBAN"));
        assertThat(result, hasJsonPath("result.pinCard"));
        assertThat(result, hasJsonPath("result.pinCode"));

        daisyAccount = new BankAccount();
        daisyAccount.setiBAN((String) JsonPath.read(result, "result.iBAN"));
        daisyAccount.setPinCard((String) JsonPath.read(result, "result.pinCard"));
        daisyAccount.setPinCode((String) JsonPath.read(result, "result.pinCode"));
    }

    /**
     * Simulates to the first next first of the month.
     */
    public void simulateToFirstOfMonth(){
        String result = client.processRequest(getDate, new GetDate());
        assertThat(result, hasJsonPath("result"));
        assertThat(result, hasNoJsonPath("error"));
        assertThat(result, hasJsonPath("result.date"));
        Calendar calendar = getCalenderOfString((String) JsonPath.read(result, "result.date"));

        //simulate the days
        result = client.processRequest(simulateTime, new SimulateTime(getDaysTillNextFirstOfMonth(calendar), AuthToken.getAdminLoginToken(client)));
        checkSuccess(result);
    }

}
