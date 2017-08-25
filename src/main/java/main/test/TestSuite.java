package main.test;

import main.client.IClient;
import main.client.TestHttpClient;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ChildAccount.class,
        AdministrativeUserIII.class, AdministrativeUserIIIPartII.class, AdministrativeUserIIIPartIII.class, AdministrativeUserIIIPartIIII.class,
        CloseBankAccount.class,
        AdministrativeUserIII.class,
        CreditCard.class,
        AdministrativeUserII.class,
        AdministrativeUser.class,
        SpendingLimits.class})
public class TestSuite {

    public static IClient client = new TestHttpClient();

    @AfterClass
    public static void printRequestCount() {
        System.out.println(String.format("Test done executing. %s request were sent.", client.getRequestCount()));
    }


}
