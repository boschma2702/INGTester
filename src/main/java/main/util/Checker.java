package main.util;

import static org.hamcrest.Matchers.equalTo;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.*;
import static org.junit.Assert.assertThat;

public class Checker {

    /**
     * Checks if a given return message has an empty result field and no error field.
     * @param message the return json rpc message from the server
     */
    public static void checkSuccess(String message){
        assertThat(message, hasJsonPath("result.length()", equalTo(0)));
        assertThat(message, hasNoJsonPath("error"));
    }

    /**
     * Checks if the return message contains an error field and checks if this object has a code field with corresponding
     * errorCode. It also check that the message does not contain a result field.
     * @param message the return json rpc message from the server
     * @param errorCode errorCode the message should contain
     */
    public static void checkError(String message, int errorCode){
        assertThat(message, hasNoJsonPath("result"));
        assertThat(message, hasJsonPath("error"));
        assertThat(message, hasJsonPath("error.code", equalTo(errorCode)));
    }
}
