package main.test;

import main.client.IClient;
import main.client.TestHttpClient;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.Assert.assertThat;

@RunWith(Suite.class)
@Suite.SuiteClasses({AdministrateUser.class, SpendingLimits.class})
public class TestSuite {

    public static IClient client = new TestHttpClient();



}
