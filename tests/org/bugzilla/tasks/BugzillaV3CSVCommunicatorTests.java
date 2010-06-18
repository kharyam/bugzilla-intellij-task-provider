package org.bugzilla.tasks;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: nickhristov
 * Date: Jun 18, 2010
 * Time: 5:09:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class BugzillaV3CSVCommunicatorTests {

    @Test public void testQuoteStripping() {
        String value = "\"my text\"";
        value = BugzillaV3CSVCommunicator.stripQuotes(value);
        assert value.equals("my text");
    }

    @Test public void testQuoteStripping2() {
        String value = "\"my \"text\"";
        value = BugzillaV3CSVCommunicator.stripQuotes(value);
        assert value.equals("my \"text");
    }

    @Test public void testQuoteStripping3() {
        String value = "my \"text";
        value = BugzillaV3CSVCommunicator.stripQuotes(value);
        assert value.equals("my \"text");
    }

}
