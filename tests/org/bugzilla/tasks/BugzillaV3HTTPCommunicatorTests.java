package org.bugzilla.tasks;

import org.junit.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by IntelliJ IDEA.
 * User: nickhristov
 * Date: Jun 18, 2010
 * Time: 5:34:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class BugzillaV3HTTPCommunicatorTests {
    BugzillaV3HTTPCommunicator communicator;
    @Before public void init() throws URISyntaxException {
        communicator = new BugzillaV3CSVCommunicator(new URI("http://bugzilla.mozilla.com/"));
    }

    @Test
    public void testLogin() throws IOException {
        AuthToken token = communicator.login(System.getProperty("username"), System.getProperty("password"));
        assert token != null;
    }

    @Test
    public void testLoginFailure() throws IOException {
        AuthToken token = communicator.login("bad", "bad");
        assert token == null;
    }

    @Test public void testQuery() throws IOException, URISyntaxException {
        AuthToken token = communicator.login(System.getProperty("username"), System.getProperty("password"));
        BugzillaQuery query = new BugzillaQuery();
        query.setAssignedTo("nickhristov");
        query.setTargetMilestone("10.06.2");
        BugzillaTask[] tasks = communicator.getIssues(token, query);
        assert tasks != null;

    }
}
